package model.credit

import scala.beans.BeanProperty

case class CustomerData(
    @BeanProperty
    id: String,
    @BeanProperty
    creditScore: Int
)

case class CreditLine(
    @BeanProperty
    customerData: CustomerData,
    @BeanProperty
    creditLimit: Int
)

trait Application {

  val customerId: String
  val creditLine: Option[CreditLine]

  def approved(creditLine: CreditLine): Application = {
    ApprovedApplication(customerId, Some(creditLine))
  }

  def unapproved: Application = {
    UnapprovedApplication(customerId)
  }
}

case class PendingApplication(
    @BeanProperty
    customerId: String,
    @BeanProperty
    creditLine: Option[CreditLine] = None
) extends Application

case class ApprovedApplication(
    @BeanProperty
    customerId: String,
    @BeanProperty
    creditLine: Option[CreditLine]
) extends Application

case class UnapprovedApplication(
    @BeanProperty
    customerId: String,
    @BeanProperty
    creditLine: Option[CreditLine] = None
) extends Application
