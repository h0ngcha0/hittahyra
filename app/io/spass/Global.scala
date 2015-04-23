package io.spass

import java.lang.reflect.Constructor

import io.spass.users.controllers.{AngularMailTemplates, JsonViewTemplates}
import io.spass.users.models.BasicUser
import io.spass.users.service.slick.{SlickAuthenticatorStore, SlickUserService}
import securesocial.controllers.{MailTemplates, ViewTemplates}
import securesocial.core.RuntimeEnvironment
import securesocial.core.authenticator.{CookieAuthenticatorBuilder, HttpHeaderAuthenticatorBuilder}
import securesocial.core.services.{AuthenticatorService, UserService}

/**
 * @author Joseph Dessens
 * @since 2014-08-31
 */
object Global extends play.api.GlobalSettings {
  object MyRuntimeEnvironment extends RuntimeEnvironment.Default[BasicUser] {
    override val userService: UserService[BasicUser] = new SlickUserService
    override lazy val authenticatorService: AuthenticatorService[BasicUser] = new AuthenticatorService[BasicUser](
      new CookieAuthenticatorBuilder[BasicUser](new SlickAuthenticatorStore, idGenerator),
      new HttpHeaderAuthenticatorBuilder[BasicUser](new SlickAuthenticatorStore, idGenerator)
    )
    override lazy val viewTemplates: ViewTemplates = JsonViewTemplates
    override lazy val mailTemplates: MailTemplates = AngularMailTemplates
  }

  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    val instance  = controllerClass.getConstructors.find { c =>
      val params = c.getParameterTypes
      params.length == 1 && params(0) == classOf[RuntimeEnvironment[BasicUser]]
    }.map {
      _.asInstanceOf[Constructor[A]].newInstance(MyRuntimeEnvironment)
    }
    instance.getOrElse(super.getControllerInstance(controllerClass))
  }
}
