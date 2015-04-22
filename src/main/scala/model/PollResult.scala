package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class PollResult(
  id: Long,
  name: String,
  email: String,
  description: String,
  role: Long,
  createdAt: DateTime,
  updatedAt: DateTime
)

object PollResult extends SkinnyCRUDMapper[PollResult] with TimestampsFeature[PollResult] {
  override lazy val tableName = "poll_results"
  override lazy val defaultAlias = createAlias("pr")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[PollResult]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[PollResult]): PollResult = new PollResult(
    id = rs.get(rn.id),
    name = rs.get(rn.name),
    email = rs.get(rn.email),
    description = rs.get(rn.description),
    role = rs.get(rn.role),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
