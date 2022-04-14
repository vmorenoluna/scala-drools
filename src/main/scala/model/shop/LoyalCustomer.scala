package model.shop

import scala.beans.BeanProperty

case class LoyalCustomer(
    @BeanProperty
    user: User,
    @BeanProperty
    level: Int
)
