package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    pollResults.mount(ctx)
    employeeRoles.mount(ctx)
    root.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
  }
  object employeeRoles extends _root_.controller.EmployeeRolesController with Routes {
  }

  object pollResults extends _root_.controller.PollResultsController with Routes {
  }

}
