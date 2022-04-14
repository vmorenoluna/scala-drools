package model.shop

import scala.beans.BeanProperty

case class Purchase(
    @BeanProperty
    user: User,
    @BeanProperty
    numberOfItems: Int
)
