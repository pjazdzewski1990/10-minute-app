package integrationtest

import org.scalatra.test.scalatest._
import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class EmployeeRolesController_IntegrationTestSpec extends ScalatraFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.employeeRoles, "/*")

  override def afterAll() {
    super.afterAll()
    EmployeeRole.deleteAll()
  }

  def newEmployeeRole = FactoryGirl(EmployeeRole).create()

  it should "show employee roles" in {
    get("/employee_roles") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/employee_roles/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/employee_roles.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/employee_roles.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a employee role in detail" in {
    get(s"/employee_roles/${newEmployeeRole.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/employee_roles/${newEmployeeRole.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/employee_roles/${newEmployeeRole.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/employee_roles/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a employee role" in {
    post(s"/employee_roles",
      "name" -> "dummy",
      "technology" -> "dummy",
      "responsibilities" -> "dummy",
      "min_experience" -> Int.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/employee_roles",
        "name" -> "dummy",
        "technology" -> "dummy",
        "responsibilities" -> "dummy",
        "min_experience" -> Int.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        EmployeeRole.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/employee_roles/${newEmployeeRole.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a employee role" in {
    put(s"/employee_roles/${newEmployeeRole.id}",
      "name" -> "dummy",
      "technology" -> "dummy",
      "responsibilities" -> "dummy",
      "min_experience" -> Int.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/employee_roles/${newEmployeeRole.id}",
        "name" -> "dummy",
        "technology" -> "dummy",
        "responsibilities" -> "dummy",
        "min_experience" -> Int.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a employee role" in {
    delete(s"/employee_roles/${newEmployeeRole.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/employee_roles/${newEmployeeRole.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
