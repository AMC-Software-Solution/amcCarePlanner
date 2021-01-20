import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/extra_data/bloc/extra_data_bloc.dart';
import 'package:amcCarePlanner/entities/extra_data/extra_data_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'extra_data_route.dart';

class ExtraDataViewScreen extends StatelessWidget {
  ExtraDataViewScreen({Key key}) : super(key: ExtraDataRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesExtraDataViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ExtraDataBloc, ExtraDataState>(
              buildWhen: (previous, current) => previous.loadedExtraData != current.loadedExtraData,
              builder: (context, state) {
                return Visibility(
                  visible: state.extraDataStatusUI == ExtraDataStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    extraDataCard(state.loadedExtraData, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ExtraDataRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget extraDataCard(ExtraData extraData, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + extraData.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Entity Name : ' + extraData.entityName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Entity Id : ' + extraData.entityId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Extra Data Key : ' + extraData.extraDataKey.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Extra Data Value : ' + extraData.extraDataValue.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Extra Data Value Data Type : ' + extraData.extraDataValueDataType.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Extra Data Description : ' + extraData.extraDataDescription.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Extra Data Date : ' + (extraData?.extraDataDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(extraData.extraDataDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + extraData.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
