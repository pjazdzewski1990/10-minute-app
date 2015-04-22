package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class EmployeeRolesControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    EmployeeRole.deleteAll()
  }

  def createMockController = new EmployeeRolesController with MockController
  def newEmployeeRole = FactoryGirl(EmployeeRole).create()

  describe("EmployeeRolesController") {

    describe("shows employee roles") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/employeeRoles/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/employeeRoles/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a employee role") {
      it("shows HTML response") {
        val employeeRole = newEmployeeRole
        val controller = createMockController
        controller.showResource(employeeRole.id)
        controller.status should equal(200)
        controller.getFromRequestScope[EmployeeRole]("item") should equal(Some(employeeRole))
        controller.renderCall.map(_.path) should equal(Some("/employeeRoles/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/employeeRoles/new"))
      }
    }

    describe("creates a employee role") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "name" -> "dummy",
          "technology" -> "dummy",
          "responsibilities" -> "dummy",
          "min_experience" -> Int.MaxValue.toString())
        controller.createResource()
        controller.status should equal(200)
      }

      it("fails with invalid parameters") {
        val controller = createMockController
        controller.prepareParams() // no parameters
        controller.createResource()
        controller.status should equal(400)
        controller.errorMessages.size should be >(0)
      }
    }

    it("shows a resource edit input form") {
      val employeeRole = newEmployeeRole
      val controller = createMockController
      controller.editResource(employeeRole.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/employeeRoles/edit"))
    }

    it("updates a employee role") {
      val employeeRole = newEmployeeRole
      val controller = createMockController
      controller.prepareParams(
        "name" -> "dummy",
        "technology" -> "dummy",
        "responsibilities" -> "dummy",
        "min_experience" -> Int.MaxValue.toString())
      controller.updateResource(employeeRole.id)
      controller.status should equal(200)
    }

    it("destroys a employee role") {
      val employeeRole = newEmployeeRole
      val controller = createMockController
      controller.destroyResource(employeeRole.id)
      controller.status should equal(200)
    }

  }

}
