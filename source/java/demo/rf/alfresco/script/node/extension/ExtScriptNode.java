package demo.rf.alfresco.script.node.extension;

import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.repo.rule.RuleModel;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

public class ExtScriptNode extends BaseProcessorExtension {

	private NodeService nodeService;

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void addNode(ScriptNode parent, ScriptNode child,
			String assocTypeQName, String qname) {
		nodeService.addChild(parent.getNodeRef(), child.getNodeRef(),
				QName.createQName(assocTypeQName), QName.createQName(qname));
	}
	
	public void linkRuleSet(ScriptNode source,ScriptNode target){
		nodeService.addAspect(target.getNodeRef(), RuleModel.ASPECT_RULES, null);
		nodeService.removeChild(target.getNodeRef(),
				nodeService.getChildAssocs(target.getNodeRef()).get(0).getChildRef());
		nodeService.addChild(target.getNodeRef(),
				nodeService.getChildAssocs(source.getNodeRef()).get(0)
						.getChildRef(), RuleModel.ASSOC_RULE_FOLDER,
				RuleModel.ASSOC_RULE_FOLDER);
	}
}
