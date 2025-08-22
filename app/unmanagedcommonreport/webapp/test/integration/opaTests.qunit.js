sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'unmanagedcommonreport/test/integration/FirstJourney',
		'unmanagedcommonreport/test/integration/pages/AllEntitiesList',
		'unmanagedcommonreport/test/integration/pages/AllEntitiesObjectPage'
    ],
    function(JourneyRunner, opaJourney, AllEntitiesList, AllEntitiesObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('unmanagedcommonreport') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onTheAllEntitiesList: AllEntitiesList,
					onTheAllEntitiesObjectPage: AllEntitiesObjectPage
                }
            },
            opaJourney.run
        );
    }
);