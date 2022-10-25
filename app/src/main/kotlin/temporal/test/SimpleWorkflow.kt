package temporal.test

import io.temporal.activity.ActivityInterface
import io.temporal.workflow.Workflow
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface ISimpleWorkflow {
    @WorkflowMethod
    fun execute(prescriptionCreatedEvent: String): String
}

@ActivityInterface
interface ISimpleActivities {
    fun getAccountByPrescriptionId(accountId: String): String
    fun getTemplate(templateEngineRequest: String): String
    fun scheduleSMS(scheduleSMSRequest: String): String
}

class SimpleWorkflowImpl: ISimpleWorkflow {

    private val activities = Workflow.newActivityStub(ISimpleActivities::class.java)

    override fun execute(prescriptionCreatedEvent: String): String {
        val template = activities.getAccountByPrescriptionId(prescriptionCreatedEvent)
        activities.getTemplate(template)
        activities.scheduleSMS(template)
        return "Success"
    }
}

class SimpleActivitiesImpl: ISimpleActivities {
    override fun getAccountByPrescriptionId(accountId: String): String {
        return "account"
    }

    override fun getTemplate(templateEngineRequest: String): String {
        return "template"
    }

    override fun scheduleSMS(scheduleSMSRequest: String): String {
        return "scheduleSMS"
    }
}
