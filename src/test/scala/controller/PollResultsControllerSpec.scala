package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class PollResultsControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    PollResult.deleteAll()
  }

  def createMockController = new PollResultsController with MockController
  def newPollResult = FactoryGirl(PollResult).create()

  describe("PollResultsController") {

    describe("shows poll results") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/pollResults/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/pollResults/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a poll result") {
      it("shows HTML response") {
        val pollResult = newPollResult
        val controller = createMockController
        controller.showResource(pollResult.id)
        controller.status should equal(200)
        controller.getFromRequestScope[PollResult]("item") should equal(Some(pollResult))
        controller.renderCall.map(_.path) should equal(Some("/pollResults/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/pollResults/new"))
      }
    }

    describe("creates a poll result") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "name" -> "dummy",
          "email" -> "dummy",
          "description" -> "dummy",
          "role" -> Long.MaxValue.toString())
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
      val pollResult = newPollResult
      val controller = createMockController
      controller.editResource(pollResult.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/pollResults/edit"))
    }

    it("updates a poll result") {
      val pollResult = newPollResult
      val controller = createMockController
      controller.prepareParams(
        "name" -> "dummy",
        "email" -> "dummy",
        "description" -> "dummy",
        "role" -> Long.MaxValue.toString())
      controller.updateResource(pollResult.id)
      controller.status should equal(200)
    }

    it("destroys a poll result") {
      val pollResult = newPollResult
      val controller = createMockController
      controller.destroyResource(pollResult.id)
      controller.status should equal(200)
    }

  }

}
