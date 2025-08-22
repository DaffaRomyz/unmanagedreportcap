package customer.unmanagedreportcap.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.Result;
import com.sap.cds.ResultBuilder;
import com.sap.cds.ql.CQL;
import com.sap.cds.ql.Predicate;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.AnalysisResult;
import com.sap.cds.ql.cqn.CqnAnalyzer;
import com.sap.cds.ql.cqn.CqnPredicate;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnStructuredTypeRef;
import com.sap.cds.reflect.CdsEntity;
import com.sap.cds.reflect.CdsModel;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
// import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.catalogservice.CatalogService_;
import customer.unmanagedreportcap.utils.CheckDataVisitor;
import customer.unmanagedreportcap.utils.UnmanagedReportUtils;
import cds.gen.catalogservice.AllEntities;
import cds.gen.catalogservice.AllEntities_;
// import cds.gen.catalogservice.Books;

@Component
@ServiceName(CatalogService_.CDS_NAME)
public class CatalogServiceHandler implements EventHandler {

    @Autowired
    CdsModel cdsModel;

    @On(event = CqnService.EVENT_READ, entity = AllEntities_.CDS_NAME)
    public void getAllEntities(CdsReadEventContext context) {

        List<AllEntities> results = new ArrayList<AllEntities>();

        // get SELECT CQN object
        CqnSelect cqnSelect = context.getCqn();

        /** CqnVisitor */
        // filter
        // sort

        // get data and filter
        cdsModel.services().forEach((service) -> {
            String serviceName = service.getName();
            Stream<CdsEntity> entities = service.entities();
            entities.forEach((entity) -> {

                AllEntities entityResult = AllEntities.create();
                entityResult.setEntityName(entity.getName());
                entityResult.setDescription(entity.getAnnotationValue("title", null));
                entityResult.setService(serviceName);

                // filter
                CheckDataVisitor checkDataVisitor = new CheckDataVisitor(entityResult);
                // cqnSelect.where().get().accept(checkDataVisitor);
                try {
                    CqnPredicate cqnPredicate = cqnSelect.where().get();
                    cqnPredicate.accept(checkDataVisitor);
                    if (checkDataVisitor.matches()) {
                        results.add(entityResult);
                    }
                } catch (Exception e) {
                    // No where conditions
                    results.add(entityResult);
                }

            });
        });

        // sort
        UnmanagedReportUtils.sort(cqnSelect.orderBy(), results);

        // inlineCount
        long inlineCount = results.size();

        /** CqnAnalyzer */
        // CqnAnalyzer
        // CqnAnalyzer cqnAnalyzer = CqnAnalyzer.create(cdsModel);
        // CqnStructuredTypeRef cqnStructuredTypeRef = context.getCqn().ref();

        /** Top/Skip */
        // top skip
        // var context.getCqn().top();
        // context.getCqn().skip();
        List<? extends Map<String, ?>> results2 = UnmanagedReportUtils.getTopSkip(context.getCqn().top(), context.getCqn().skip(), results);

        Result result = ResultBuilder.selectedRows(results2).inlineCount(inlineCount).result();

        context.setResult(result);

    }

}