import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee/bloc/employee_bloc.dart';
import 'package:amcCarePlanner/entities/employee/employee_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'employee_route.dart';

class EmployeeViewScreen extends StatelessWidget {
  EmployeeViewScreen({Key key}) : super(key: EmployeeRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEmployeeViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeBloc, EmployeeState>(
              buildWhen: (previous, current) => previous.loadedEmployee != current.loadedEmployee,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeStatusUI == EmployeeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    employeeCard(state.loadedEmployee, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget employeeCard(Employee employee, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + employee.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Title : ' + employee.title.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('First Name : ' + employee.firstName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Middle Initial : ' + employee.middleInitial.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Last Name : ' + employee.lastName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Preferred Name : ' + employee.preferredName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Gender : ' + employee.gender.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Employee Code : ' + employee.employeeCode.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Phone : ' + employee.phone.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Email : ' + employee.email.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('National Insurance Number : ' + employee.nationalInsuranceNumber.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Employee Contract Type : ' + employee.employeeContractType.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Pin Code : ' + employee.pinCode.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Transport Mode : ' + employee.transportMode.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Address : ' + employee.address.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('County : ' + employee.county.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Post Code : ' + employee.postCode.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Date Of Birth : ' + (employee?.dateOfBirth != null ? DateFormat.yMMMMd(S.of(context).locale).format(employee.dateOfBirth) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Photo Url : ' + employee.photoUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Status : ' + employee.status.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Employee Bio : ' + employee.employeeBio.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Acrued Holiday Hours : ' + employee.acruedHolidayHours.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (employee?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employee.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (employee?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employee.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + employee.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + employee.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
