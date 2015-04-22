package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.PollResult

class PollResultsController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = PollResult
  override def resourcesName = "pollResults"
  override def resourceName = "pollResult"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("name") is required & maxLength(512),
    paramKey("email") is required & maxLength(512) & email,
    paramKey("description") is required & maxLength(512),
    paramKey("role") is required & numeric & longValue
  )
  override def createFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "email" -> ParamType.String,
    "description" -> ParamType.String,
    "role" -> ParamType.Long
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("name") is required & maxLength(512),
    paramKey("email") is required & maxLength(512),
    paramKey("description") is required & maxLength(512),
    paramKey("role") is required & numeric & longValue
  )
  override def updateFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "email" -> ParamType.String,
    "description" -> ParamType.String,
    "role" -> ParamType.Long
  )

}
