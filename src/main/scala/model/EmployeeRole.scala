package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class EmployeeRole(
  id: Long,
  name: String,
  technology: String,
  responsibilities: String,
  minExperience: Int,
  createdAt: DateTime,
  updatedAt: DateTime
)

object EmployeeRole extends SkinnyCRUDMapper[EmployeeRole] with TimestampsFeature[EmployeeRole] {
  override lazy val tableName = "employee_roles"
  override lazy val defaultAlias = createAlias("er")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[EmployeeRole]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[EmployeeRole]): EmployeeRole = new EmployeeRole(
    id = rs.get(rn.id),
    name = rs.get(rn.name),
    technology = rs.get(rn.technology),
    responsibilities = rs.get(rn.responsibilities),
    minExperience = rs.get(rn.minExperience),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
