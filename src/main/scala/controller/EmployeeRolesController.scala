package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.EmployeeRole

class EmployeeRolesController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = EmployeeRole
  override def resourcesName = "employeeRoles"
  override def resourceName = "employeeRole"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("name") is required & maxLength(512),
    paramKey("technology") is required & maxLength(512),
    paramKey("responsibilities") is required & maxLength(512),
    paramKey("min_experience") is required & numeric & intValue
  )
  override def createFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "technology" -> ParamType.String,
    "responsibilities" -> ParamType.String,
    "min_experience" -> ParamType.Int
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("name") is required & maxLength(512),
    paramKey("technology") is required & maxLength(512),
    paramKey("responsibilities") is required & maxLength(512),
    paramKey("min_experience") is required & numeric & intValue
  )
  override def updateFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "technology" -> ParamType.String,
    "responsibilities" -> ParamType.String,
    "min_experience" -> ParamType.Int
  )

}
