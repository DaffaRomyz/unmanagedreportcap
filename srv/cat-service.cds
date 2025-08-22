using unmanagedreport from '../db/schema';

service CatalogService {
    @readonly
    entity AllEntities as projection on unmanagedreport.AllEntities;
    
    @readonly
    entity Books       as projection on unmanagedreport.Books;

    @readonly
    @title: 'Authors'
    entity Authors     as projection on unmanagedreport.Authors;

    @readonly
    @title: 'Orders'
    entity Orders      as projection on unmanagedreport.Orders;

    @readonly
    @title:'Order Items'
    entity OrderItems  as projection on unmanagedreport.OrderItems;
    
    @readonly
    entity OrderUnmanaged as projection on unmanagedreport.OrderUnmanaged;
}