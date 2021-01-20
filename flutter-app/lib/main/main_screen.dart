import 'package:flutter/cupertino.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/keys.dart';
import 'package:amcCarePlanner/main/bloc/main_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

class MainScreen extends StatelessWidget {
  MainScreen({Key key}) : super(key: AmcCarePlannerKeys.mainScreen);

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<MainBloc, MainState>(
      buildWhen: (previous, current) =>
          previous.currentUser != current.currentUser,
      builder: (context, state) {
        return Scaffold(
            appBar: AppBar(
              centerTitle: true,
              title: Text(S.of(context).pageMainTitle),
            ),
            body: body(context),
            drawer: BlocProvider<DrawerBloc>(
                create: (context) =>
                    DrawerBloc(loginRepository: LoginRepository()),
                child: AmcCarePlannerDrawer()));
      },
    );
  }

  Widget body(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Image(
              image: AssetImage(
                  'assets/images/jhipster_family_member_0_head-192.png'),
            ),
            Padding(
              padding: EdgeInsets.symmetric(vertical: 25),
            ),
            currentUserWidget(context),
            Padding(
              padding: EdgeInsets.symmetric(vertical: 10),
            ),
            linkWidget(context, 'CarePlanner Repo',
                'https://github.com/AMC-Software-Solution/amcCarePlanner'),
            linkWidget(context, 'AMC Repo',
                'https://github.com/AMC-Software-Solution'),
          ],
        ),
      ],
    );
  }

  Widget currentUserWidget(BuildContext context) {
    return BlocBuilder<MainBloc, MainState>(
        buildWhen: (previous, current) =>
            previous.currentUser != current.currentUser,
        builder: (context, state) {
          String user = state.currentUser.firstName;
          return Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(S.of(context).pageMainCurrentUser(user),
                  style: Theme.of(context).textTheme.headline3),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 5),
              ),
              SizedBox(
                  width: MediaQuery.of(context).size.width * 0.80,
                  child: Text(S.of(context).pageMainWelcome,
                      textAlign: TextAlign.center,
                      style: Theme.of(context).textTheme.headline3)),
            ],
          );
        });
  }

  Widget linkWidget(BuildContext context, String text, String url) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 15),
      child: RaisedButton(
          onPressed: () => _launchURL(url),
          child: Container(
            child: Text(
              text,
              textAlign: TextAlign.center,
            ),
            width: MediaQuery.of(context).size.width * 0.5,
          )),
    );
  }

  _launchURL(String url) async {
    if (await canLaunch(url)) {
      await launch(url);
    } else {
      throw 'Could not launch $url';
    }
  }
}
