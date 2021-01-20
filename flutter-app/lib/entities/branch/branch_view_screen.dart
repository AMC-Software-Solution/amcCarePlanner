import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/branch/bloc/branch_bloc.dart';
import 'package:amcCarePlanner/entities/branch/branch_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'branch_route.dart';

class BranchViewScreen extends StatelessWidget {
  BranchViewScreen({Key key}) : super(key: BranchRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesBranchViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<BranchBloc, BranchState>(
              buildWhen: (previous, current) => previous.loadedBranch != current.loadedBranch,
              builder: (context, state) {
                return Visibility(
                  visible: state.branchStatusUI == BranchStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    branchCard(state.loadedBranch, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, BranchRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget branchCard(Branch branch, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + branch.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Name : ' + branch.name.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Address : ' + branch.address.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Telephone : ' + branch.telephone.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Contact Name : ' + branch.contactName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Branch Email : ' + branch.branchEmail.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Photo Url : ' + branch.photoUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (branch?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(branch.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (branch?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(branch.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + branch.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
