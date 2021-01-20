import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee_location/bloc/employee_location_bloc.dart';
import 'package:amcCarePlanner/entities/employee_location/employee_location_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'employee_location_route.dart';

class EmployeeLocationViewScreen extends StatelessWidget {
  EmployeeLocationViewScreen({Key key}) : super(key: EmployeeLocationRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEmployeeLocationViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeLocationBloc, EmployeeLocationState>(
              buildWhen: (previous, current) => previous.loadedEmployeeLocation != current.loadedEmployeeLocation,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeLocationStatusUI == EmployeeLocationStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    employeeLocationCard(state.loadedEmployeeLocation, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeLocationRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget employeeLocationCard(EmployeeLocation employeeLocation, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + employeeLocation.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Latitude : ' + employeeLocation.latitude.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Longitude : ' + employeeLocation.longitude.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (employeeLocation?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeLocation.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (employeeLocation?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeLocation.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + employeeLocation.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + employeeLocation.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
