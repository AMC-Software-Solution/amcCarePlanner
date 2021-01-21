import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee_document/bloc/employee_document_bloc.dart';
import 'package:amcCarePlanner/entities/employee_document/employee_document_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'employee_document_route.dart';

class EmployeeDocumentViewScreen extends StatelessWidget {
  EmployeeDocumentViewScreen({Key key}) : super(key: EmployeeDocumentRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEmployeeDocumentViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
              buildWhen: (previous, current) => previous.loadedEmployeeDocument != current.loadedEmployeeDocument,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeDocumentStatusUI == EmployeeDocumentStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    employeeDocumentCard(state.loadedEmployeeDocument, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeDocumentRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget employeeDocumentCard(EmployeeDocument employeeDocument, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + employeeDocument.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Name : ' + employeeDocument.documentName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Number : ' + employeeDocument.documentNumber.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Status : ' + employeeDocument.documentStatus.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + employeeDocument.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Issued Date : ' + (employeeDocument?.issuedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeDocument.issuedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Expiry Date : ' + (employeeDocument?.expiryDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeDocument.expiryDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Uploaded Date : ' + (employeeDocument?.uploadedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeDocument.uploadedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document File Url : ' + employeeDocument.documentFileUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (employeeDocument?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeDocument.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (employeeDocument?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(employeeDocument.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + employeeDocument.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + employeeDocument.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
