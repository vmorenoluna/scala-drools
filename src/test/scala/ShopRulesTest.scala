import model.shop.{ItemReturn, LoyalCustomer, Purchase, User}
import org.drools.core.ClassObjectFilter
import org.kie.api.runtime.KieContainer
import org.scalatest.Matchers.convertToAnyShouldWrapper
import org.scalatest.flatspec.AnyFlatSpec
import org.slf4j.LoggerFactory

class ShopRulesTest extends AnyFlatSpec {

  private val kieContainer: KieContainer = KnowledgeSessionHelper.createRuleBase()

  "The shop rules" should "properly work" in {

    val statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "my-shop-ksession")
    statefulSession.setGlobal("logger", LoggerFactory.getLogger(classOf[ShopRulesTest]))

    val user: User = User(id = 1, purchasedItems = 0)
    statefulSession.insert(user)
    statefulSession.fireAllRules()

    statefulSession.getObjects(new ClassObjectFilter(classOf[LoyalCustomer])).size() shouldBe 0

    val purchase1: Purchase = Purchase(user, numberOfItems = 2)
    statefulSession.insert(purchase1)
    statefulSession.fireAllRules()

    statefulSession.getObjects(new ClassObjectFilter(classOf[LoyalCustomer])).size() shouldBe 0

    val purchase2: Purchase = Purchase(user, numberOfItems = 2)
    statefulSession.insert(purchase2)
    statefulSession.fireAllRules()

    val loyalCustomerObjects1 = statefulSession.getObjects(new ClassObjectFilter(classOf[LoyalCustomer]))
    loyalCustomerObjects1.size() shouldBe 1
    loyalCustomerObjects1.iterator().next() shouldBe LoyalCustomer(user, level = 1)

    val purchase3: Purchase = Purchase(user, numberOfItems = 2)
    statefulSession.insert(purchase3)
    statefulSession.fireAllRules()

    val loyalCustomerObjects2 = statefulSession.getObjects(new ClassObjectFilter(classOf[LoyalCustomer]))
    loyalCustomerObjects2.size() shouldBe 1
    loyalCustomerObjects2.iterator().next() shouldBe LoyalCustomer(user, level = 2)

    val itemReturn: ItemReturn = ItemReturn(user, numberOfReturns = 2)
    statefulSession.insert(itemReturn)
    statefulSession.fireAllRules()

    val loyalCustomerObjects3 = statefulSession.getObjects(new ClassObjectFilter(classOf[LoyalCustomer]))
    loyalCustomerObjects3.size() shouldBe 1
    loyalCustomerObjects3.iterator().next() shouldBe LoyalCustomer(user, level = 1)

    statefulSession.dispose()

  }

}
