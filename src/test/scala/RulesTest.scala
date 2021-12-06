import model.Customer
import org.kie.api.event.rule.{ObjectDeletedEvent, ObjectInsertedEvent, ObjectUpdatedEvent, RuleRuntimeEventListener}
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.rule.FactHandle
import org.scalatest.Matchers.convertToAnyShouldWrapper
import org.scalatest.flatspec.AnyFlatSpec
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class RulesTest extends AnyFlatSpec {

  private val kieContainer: KieContainer = KnowledgeSessionHelper.createRuleBase()

  "session" should "work" in {

    val statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "my-first-ksession")
    statefulSession.addEventListener(
      new RuleRuntimeEventListener {
        override def objectInserted(event: ObjectInsertedEvent): Unit =
          println(s"Object inserted: ${event.getObject.toString}")

        override def objectUpdated(event: ObjectUpdatedEvent): Unit =
          println(s"Object updated: ${event.getObject.toString}")

        override def objectDeleted(event: ObjectDeletedEvent): Unit =
          println(s"Object deleted: ${event.getOldObject.toString}")
      }
    )
    statefulSession.setGlobal("logger", LoggerFactory.getLogger(classOf[RulesTest]))
    val customer: Customer       = new Customer("Vincenzo", 16)
    val factHandle: FactHandle = statefulSession.insert(customer)
    statefulSession.fireAllRules()
    customer.setAge(20)
    statefulSession.update(factHandle, customer)
    statefulSession.delete(factHandle)
    statefulSession.fireAllRules()

    val n = 1
    n shouldEqual 1
  }

//  "session" should "work with emotion rule" in {
//
//    val statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "rule-session")
//    statefulSession.addEventListener(
//      new RuleRuntimeEventListener {
//        override def objectInserted(event: ObjectInsertedEvent): Unit =
//          println(s"Object inserted: ${event.getObject.toString}")
//
//        override def objectUpdated(event: ObjectUpdatedEvent): Unit =
//          println(s"Object updated: ${event.getObject.toString}")
//
//        override def objectDeleted(event: ObjectDeletedEvent): Unit =
//          println(s"Object deleted: ${event.getOldObject.toString}")
//      }
//    )
//    statefulSession.setGlobal("logger", new RulesLogger)
//    val map: mutable.Map[Point, Interval] = mutable.Map()
//    statefulSession.setGlobal("map", map)
//    val emotion: Emotion = Emotion(EmotionName.JOYFUL, 0.5, 1.0)
//    statefulSession.insert(emotion)
//    val point: Point = Point(0.5, 0.5)
//    statefulSession.insert(point)
//    statefulSession.fireAllRules()
//
//    val objects = statefulSession.getObjects(new ClassObjectFilter(classOf[Emotion]))
//    objects.size shouldEqual 1
//
//    val i = objects.iterator()
//    val e = i.next().asInstanceOf[Emotion]
//    e shouldEqual (emotion)
//  }
//
//  "session" should "should pass the third test" in {
//
//    val statefulSession = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "rule2-session")
//    //    statefulSession.setGlobal("logger", new RulesLogger)
//    val emotion: Emotion = Emotion(EmotionName.JOYFUL, 1.0, 1.0)
//    statefulSession.insert(emotion)
//    val point: Point = Point(1.0, 1.0)
//    statefulSession.insert(point)
//    statefulSession.fireAllRules()
//
//    val objects = statefulSession.getObjects(new ClassObjectFilter(classOf[Emotion]))
//    objects.size shouldEqual 1
//
//    val i = objects.iterator()
//    val e = i.next().asInstanceOf[Emotion]
//    e shouldEqual (emotion)
//  }

}