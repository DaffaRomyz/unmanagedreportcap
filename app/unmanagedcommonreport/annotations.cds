using CatalogService as service from '../../srv/cat-service';
annotate service.AllEntities with @(
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Value : entityName,
            Label : 'entityName',
        },
        {
            $Type : 'UI.DataField',
            Value : Description,
            Label : 'Description',
        },
        {
            $Type : 'UI.DataField',
            Value : service,
            Label : 'service',
        },
        {
            $Type : 'UI.DataField',
            Value : namespace,
            Label : 'namespace',
        },
    ],
    UI.SelectionFields : [
        entityName,
        service,
    ],
);

annotate service.AllEntities with {
    entityName @Common.Label : 'entityName'
};

annotate service.AllEntities with {
    service @Common.Label : 'service'
};

