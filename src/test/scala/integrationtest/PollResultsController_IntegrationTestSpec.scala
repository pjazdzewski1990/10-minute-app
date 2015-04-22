package integrationtest

import org.scalatra.test.scalatest._
import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class PollResultsController_IntegrationTestSpec extends ScalatraFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.pollResults, "/*")

  override def afterAll() {
    super.afterAll()
    PollResult.deleteAll()
  }

  def newPollResult = FactoryGirl(PollResult).create()

  it should "show poll results" in {
    get("/poll_results") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/poll_results/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/poll_results.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/poll_results.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a poll result in detail" in {
    get(s"/poll_results/${newPollResult.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/poll_results/${newPollResult.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/poll_results/${newPollResult.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/poll_results/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a poll result" in {
    post(s"/poll_results",
      "name" -> "dummy",
      "email" -> "dummy",
      "description" -> "dummy",
      "role" -> Long.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/poll_results",
        "name" -> "dummy",
        "email" -> "dummy",
        "description" -> "dummy",
        "role" -> Long.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        PollResult.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/poll_results/${newPollResult.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a poll result" in {
    put(s"/poll_results/${newPollResult.id}",
      "name" -> "dummy",
      "email" -> "dummy",
      "description" -> "dummy",
      "role" -> Long.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/poll_results/${newPollResult.id}",
        "name" -> "dummy",
        "email" -> "dummy",
        "description" -> "dummy",
        "role" -> Long.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a poll result" in {
    delete(s"/poll_results/${newPollResult.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/poll_results/${newPollResult.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
