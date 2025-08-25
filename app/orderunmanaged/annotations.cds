using CatalogService as service from '../../srv/cat-service';
annotate service.OrderUnmanaged with @(
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Value : OrderNo,
        },
        {
            $Type : 'UI.DataField',
            Value : buyer,
        },
        {
            $Type : 'UI.DataField',
            Value : total,
            Label : 'total',
        },
    ],
    UI.SelectionFields : [
        OrderNo,
        buyer,
    ],
    Aggregation: {ApplySupported: {
        $Type              : 'Aggregation.ApplySupportedType',
        AggregatableProperties : [
            {
                $Type : 'Aggregation.AggregatablePropertyType',
                Property : total,
            },
        ],
        GroupableProperties: [
            buyer,
            OrderNo
        ],
        CustomAggregate #total: 'Edm.Decimal'
    }}
);

annotate service.OrderUnmanaged with {
    total  @Analytics.Measure  @Aggregation.default: #SUM @Measures.ISOCurrency : currency_code  ;
    currency @Semantics.currencyCode;
};

