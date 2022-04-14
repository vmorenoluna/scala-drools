package model.shop

import scala.beans.BeanProperty

case class ItemReturn(
    @BeanProperty
    user: User,
    @BeanProperty
    var numberOfReturns: Int
)
