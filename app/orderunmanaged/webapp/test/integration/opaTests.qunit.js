sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'orderunmanaged/test/integration/FirstJourney',
		'orderunmanaged/test/integration/pages/OrderUnmanagedList',
		'orderunmanaged/test/integration/pages/OrderUnmanagedObjectPage'
    ],
    function(JourneyRunner, opaJourney, OrderUnmanagedList, OrderUnmanagedObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('orderunmanaged') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onTheOrderUnmanagedList: OrderUnmanagedList,
					onTheOrderUnmanagedObjectPage: OrderUnmanagedObjectPage
                }
            },
            opaJourney.run
        );
    }
);