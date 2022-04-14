package model.shop

import scala.beans.BeanProperty

case class User(
    @BeanProperty
    id: Int,
    @BeanProperty
    var purchasedItems: Int
)
