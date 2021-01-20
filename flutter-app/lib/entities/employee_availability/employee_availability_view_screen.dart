import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee_availability/bloc/employee_availability_bloc.dart';
import 'package:amcCarePlanner/entities/employee_availability/employee_availability_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'employee_availability_route.dart';

class EmployeeAvailabilityViewScreen extends StatelessWidget {
  EmployeeAvailabilityViewScreen({Key key}) : super(key: EmployeeAvailabilityRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEmployeeAvailabilityViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeAvailabilityBloc, EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.loadedEmployeeAvailability != current.loadedEmployeeAvailability,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeAvailabilityStatusUI == EmployeeAvailabilityStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    employeeAvailabilityCard(state.loadedEmployeeAvailability, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeAvailabilityRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget employeeAvailabilityCard(EmployeeAvailability employeeAvailability, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + employeeAvailability.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Available For Work : ' + employeeAvailability.availableForWork.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Available Monday : ' + employeeAvailability.availableMonday.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Available Tuesday : ' + employeeAvailability.availableTuesday.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Available Wednesday : ' + employeeAvailability.availableWednesday.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Available Thursday : ' + employeeAvailability.availableThursday.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Available Friday : ' + employeeAvailability.availableFriday.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Available Saturday : ' + employeeAvailability.availableSaturday.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Available Sunday : ' + employeeAvailability.availableSunday.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Preferred Shift : ' + employeeAvailability.preferredShift.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + employeeAvailability.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (employeeAvailability?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeAvailability.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + employeeAvailability.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
