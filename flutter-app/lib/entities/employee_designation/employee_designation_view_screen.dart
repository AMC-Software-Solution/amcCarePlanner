import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee_designation/bloc/employee_designation_bloc.dart';
import 'package:amcCarePlanner/entities/employee_designation/employee_designation_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'employee_designation_route.dart';

class EmployeeDesignationViewScreen extends StatelessWidget {
  EmployeeDesignationViewScreen({Key key}) : super(key: EmployeeDesignationRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEmployeeDesignationViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeDesignationBloc, EmployeeDesignationState>(
              buildWhen: (previous, current) => previous.loadedEmployeeDesignation != current.loadedEmployeeDesignation,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeDesignationStatusUI == EmployeeDesignationStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    employeeDesignationCard(state.loadedEmployeeDesignation, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeDesignationRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget employeeDesignationCard(EmployeeDesignation employeeDesignation, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + employeeDesignation.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Designation : ' + employeeDesignation.designation.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + employeeDesignation.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Designation Date : ' + employeeDesignation.designationDate.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + employeeDesignation.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + employeeDesignation.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
