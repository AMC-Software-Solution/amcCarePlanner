// GENERATED CODE - DO NOT MODIFY BY HAND
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'intl/messages_all.dart';

// **************************************************************************
// Generator: Flutter Intl IDE plugin
// Made by Localizely
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, lines_longer_than_80_chars

class S {
  S();
  
  static S current;
  
  static const AppLocalizationDelegate delegate =
    AppLocalizationDelegate();

  static Future<S> load(Locale locale) {
    final name = (locale.countryCode?.isEmpty ?? false) ? locale.languageCode : locale.toString();
    final localeName = Intl.canonicalizedLocale(name); 
    return initializeMessages(localeName).then((_) {
      Intl.defaultLocale = localeName;
      S.current = S();
      
      return S.current;
    });
  } 

  static S of(BuildContext context) {
    return Localizations.of<S>(context, S);
  }

  /// `en`
  String get locale {
    return Intl.message(
      'en',
      name: 'locale',
      desc: '',
      args: [],
    );
  }

  /// `Login`
  String get pageLoginBar {
    return Intl.message(
      'Login',
      name: 'pageLoginBar',
      desc: '',
      args: [],
    );
  }

  /// `Welcome to Jhipster flutter app`
  String get pageLoginTitle {
    return Intl.message(
      'Welcome to Jhipster flutter app',
      name: 'pageLoginTitle',
      desc: '',
      args: [],
    );
  }

  /// `Sign in`
  String get pageLoginLoginButton {
    return Intl.message(
      'Sign in',
      name: 'pageLoginLoginButton',
      desc: '',
      args: [],
    );
  }

  /// `Register`
  String get pageLoginRegisterButton {
    return Intl.message(
      'Register',
      name: 'pageLoginRegisterButton',
      desc: '',
      args: [],
    );
  }

  /// `Problem when authenticate, verify your credential`
  String get pageLoginErrorAuthentication {
    return Intl.message(
      'Problem when authenticate, verify your credential',
      name: 'pageLoginErrorAuthentication',
      desc: '',
      args: [],
    );
  }

  /// `Register`
  String get pageRegisterTitle {
    return Intl.message(
      'Register',
      name: 'pageRegisterTitle',
      desc: '',
      args: [],
    );
  }

  /// `Login`
  String get pageRegisterFormLogin {
    return Intl.message(
      'Login',
      name: 'pageRegisterFormLogin',
      desc: '',
      args: [],
    );
  }

  /// `Email`
  String get pageRegisterFormEmail {
    return Intl.message(
      'Email',
      name: 'pageRegisterFormEmail',
      desc: '',
      args: [],
    );
  }

  /// `you@example.com`
  String get pageRegisterFormEmailHint {
    return Intl.message(
      'you@example.com',
      name: 'pageRegisterFormEmailHint',
      desc: '',
      args: [],
    );
  }

  /// `Password`
  String get pageRegisterFormPassword {
    return Intl.message(
      'Password',
      name: 'pageRegisterFormPassword',
      desc: '',
      args: [],
    );
  }

  /// `Confirm password`
  String get pageRegisterFormConfirmPassword {
    return Intl.message(
      'Confirm password',
      name: 'pageRegisterFormConfirmPassword',
      desc: '',
      args: [],
    );
  }

  /// `I accept the terms of use`
  String get pageRegisterFormTermsConditions {
    return Intl.message(
      'I accept the terms of use',
      name: 'pageRegisterFormTermsConditions',
      desc: '',
      args: [],
    );
  }

  /// `Please accept the terms and conditions`
  String get pageRegisterFormTermsConditionsNotChecked {
    return Intl.message(
      'Please accept the terms and conditions',
      name: 'pageRegisterFormTermsConditionsNotChecked',
      desc: '',
      args: [],
    );
  }

  /// `Sign up`
  String get pageRegisterFormSubmit {
    return Intl.message(
      'Sign up',
      name: 'pageRegisterFormSubmit',
      desc: '',
      args: [],
    );
  }

  /// `Email already exist`
  String get pageRegisterErrorMailExist {
    return Intl.message(
      'Email already exist',
      name: 'pageRegisterErrorMailExist',
      desc: '',
      args: [],
    );
  }

  /// `Login already taken`
  String get pageRegisterErrorLoginExist {
    return Intl.message(
      'Login already taken',
      name: 'pageRegisterErrorLoginExist',
      desc: '',
      args: [],
    );
  }

  /// `The passwords are not identical`
  String get pageRegisterErrorPasswordNotIdentical {
    return Intl.message(
      'The passwords are not identical',
      name: 'pageRegisterErrorPasswordNotIdentical',
      desc: '',
      args: [],
    );
  }

  /// `Register success`
  String get pageRegisterSuccessAltImg {
    return Intl.message(
      'Register success',
      name: 'pageRegisterSuccessAltImg',
      desc: '',
      args: [],
    );
  }

  /// `Congratulation`
  String get pageRegisterSuccess {
    return Intl.message(
      'Congratulation',
      name: 'pageRegisterSuccess',
      desc: '',
      args: [],
    );
  }

  /// `You have successfuly registered`
  String get pageRegisterSuccessSub {
    return Intl.message(
      'You have successfuly registered',
      name: 'pageRegisterSuccessSub',
      desc: '',
      args: [],
    );
  }

  /// `The login has to contain more than {min}`
  String pageRegisterLoginValidationError(Object min) {
    return Intl.message(
      'The login has to contain more than $min',
      name: 'pageRegisterLoginValidationError',
      desc: '',
      args: [min],
    );
  }

  /// `Please enter a valid address email`
  String get pageRegisterMailValidationError {
    return Intl.message(
      'Please enter a valid address email',
      name: 'pageRegisterMailValidationError',
      desc: '',
      args: [],
    );
  }

  /// `Rules : 1 uppercase, 1 number and {min} characters`
  String pageRegisterPasswordValidationError(Object min) {
    return Intl.message(
      'Rules : 1 uppercase, 1 number and $min characters',
      name: 'pageRegisterPasswordValidationError',
      desc: '',
      args: [min],
    );
  }

  /// `Rules : 1 uppercase, 1 number and {min} characters`
  String pageRegisterConfirmationPasswordValidationError(Object min) {
    return Intl.message(
      'Rules : 1 uppercase, 1 number and $min characters',
      name: 'pageRegisterConfirmationPasswordValidationError',
      desc: '',
      args: [min],
    );
  }

  /// `Your profile`
  String get pageMainProfileButton {
    return Intl.message(
      'Your profile',
      name: 'pageMainProfileButton',
      desc: '',
      args: [],
    );
  }

  /// `Event`
  String get pageMainEventButton {
    return Intl.message(
      'Event',
      name: 'pageMainEventButton',
      desc: '',
      args: [],
    );
  }

  /// `Open pack`
  String get pageMainOpenPackButton {
    return Intl.message(
      'Open pack',
      name: 'pageMainOpenPackButton',
      desc: '',
      args: [],
    );
  }

  /// `Packs`
  String get pageMainNumberPackOpen {
    return Intl.message(
      'Packs',
      name: 'pageMainNumberPackOpen',
      desc: '',
      args: [],
    );
  }

  /// `Marketplace`
  String get pageMainMarketButton {
    return Intl.message(
      'Marketplace',
      name: 'pageMainMarketButton',
      desc: '',
      args: [],
    );
  }

  /// `Main page`
  String get pageMainTitle {
    return Intl.message(
      'Main page',
      name: 'pageMainTitle',
      desc: '',
      args: [],
    );
  }

  /// `Current user : {login}`
  String pageMainCurrentUser(Object login) {
    return Intl.message(
      'Current user : $login',
      name: 'pageMainCurrentUser',
      desc: '',
      args: [login],
    );
  }

  /// `Welcome to your Jhipster flutter app`
  String get pageMainWelcome {
    return Intl.message(
      'Welcome to your Jhipster flutter app',
      name: 'pageMainWelcome',
      desc: '',
      args: [],
    );
  }

  /// `Settings`
  String get drawerSettingsTitle {
    return Intl.message(
      'Settings',
      name: 'drawerSettingsTitle',
      desc: '',
      args: [],
    );
  }

  /// `Sign out`
  String get drawerLogoutTitle {
    return Intl.message(
      'Sign out',
      name: 'drawerLogoutTitle',
      desc: '',
      args: [],
    );
  }

  /// `Menu`
  String get drawerMenuTitle {
    return Intl.message(
      'Menu',
      name: 'drawerMenuTitle',
      desc: '',
      args: [],
    );
  }

  /// `Home`
  String get drawerMenuMain {
    return Intl.message(
      'Home',
      name: 'drawerMenuMain',
      desc: '',
      args: [],
    );
  }

  /// `Settings`
  String get pageSettingsTitle {
    return Intl.message(
      'Settings',
      name: 'pageSettingsTitle',
      desc: '',
      args: [],
    );
  }

  /// `Firstname`
  String get pageSettingsFormFirstname {
    return Intl.message(
      'Firstname',
      name: 'pageSettingsFormFirstname',
      desc: '',
      args: [],
    );
  }

  /// `Lastname`
  String get pageSettingsFormLastname {
    return Intl.message(
      'Lastname',
      name: 'pageSettingsFormLastname',
      desc: '',
      args: [],
    );
  }

  /// `Email`
  String get pageSettingsFormEmail {
    return Intl.message(
      'Email',
      name: 'pageSettingsFormEmail',
      desc: '',
      args: [],
    );
  }

  /// `Languages`
  String get pageSettingsFormLanguages {
    return Intl.message(
      'Languages',
      name: 'pageSettingsFormLanguages',
      desc: '',
      args: [],
    );
  }

  /// `Save`
  String get pageSettingsFormSave {
    return Intl.message(
      'Save',
      name: 'pageSettingsFormSave',
      desc: '',
      args: [],
    );
  }

  /// `Settings saved !`
  String get pageSettingsSave {
    return Intl.message(
      'Settings saved !',
      name: 'pageSettingsSave',
      desc: '',
      args: [],
    );
  }

  /// `Firstname has to be {min} characters minimum`
  String pageSettingsFirstnameErrorValidation(Object min) {
    return Intl.message(
      'Firstname has to be $min characters minimum',
      name: 'pageSettingsFirstnameErrorValidation',
      desc: '',
      args: [min],
    );
  }

  /// `Lastname has to be {min} characters minimum`
  String pageSettingsLastnameErrorValidation(Object min) {
    return Intl.message(
      'Lastname has to be $min characters minimum',
      name: 'pageSettingsLastnameErrorValidation',
      desc: '',
      args: [min],
    );
  }

  /// `Email format incorrect`
  String get pageSettingsEmailErrorValidation {
    return Intl.message(
      'Email format incorrect',
      name: 'pageSettingsEmailErrorValidation',
      desc: '',
      args: [],
    );
  }

  /// `Something wrong happened with the received data`
  String get genericErrorBadRequest {
    return Intl.message(
      'Something wrong happened with the received data',
      name: 'genericErrorBadRequest',
      desc: '',
      args: [],
    );
  }

  /// `Something wrong when calling the server, please try again`
  String get genericErrorServer {
    return Intl.message(
      'Something wrong when calling the server, please try again',
      name: 'genericErrorServer',
      desc: '',
      args: [],
    );
  }

  /// `Loading...`
  String get loadingLabel {
    return Intl.message(
      'Loading...',
      name: 'loadingLabel',
      desc: '',
      args: [],
    );
  }

  /// `View`
  String get entityActionView {
    return Intl.message(
      'View',
      name: 'entityActionView',
      desc: '',
      args: [],
    );
  }

  /// `Edit`
  String get entityActionEdit {
    return Intl.message(
      'Edit',
      name: 'entityActionEdit',
      desc: '',
      args: [],
    );
  }

  /// `Create`
  String get entityActionCreate {
    return Intl.message(
      'Create',
      name: 'entityActionCreate',
      desc: '',
      args: [],
    );
  }

  /// `Delete`
  String get entityActionDelete {
    return Intl.message(
      'Delete',
      name: 'entityActionDelete',
      desc: '',
      args: [],
    );
  }

  /// `Are you sure?`
  String get entityActionConfirmDelete {
    return Intl.message(
      'Are you sure?',
      name: 'entityActionConfirmDelete',
      desc: '',
      args: [],
    );
  }

  /// `Yes`
  String get entityActionConfirmDeleteYes {
    return Intl.message(
      'Yes',
      name: 'entityActionConfirmDeleteYes',
      desc: '',
      args: [],
    );
  }

  /// `No`
  String get entityActionConfirmDeleteNo {
    return Intl.message(
      'No',
      name: 'entityActionConfirmDeleteNo',
      desc: '',
      args: [],
    );
  }

  /// `Accesses list`
  String get pageEntitiesAccessListTitle {
    return Intl.message(
      'Accesses list',
      name: 'pageEntitiesAccessListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Accesses`
  String get pageEntitiesAccessUpdateTitle {
    return Intl.message(
      'Edit Accesses',
      name: 'pageEntitiesAccessUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Accesses`
  String get pageEntitiesAccessCreateTitle {
    return Intl.message(
      'Create Accesses',
      name: 'pageEntitiesAccessCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Accesses View`
  String get pageEntitiesAccessViewTitle {
    return Intl.message(
      'Accesses View',
      name: 'pageEntitiesAccessViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Accesses`
  String get pageEntitiesAccessDeletePopupTitle {
    return Intl.message(
      'Delete Accesses',
      name: 'pageEntitiesAccessDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Access deleted successfuly`
  String get pageEntitiesAccessDeleteOk {
    return Intl.message(
      'Access deleted successfuly',
      name: 'pageEntitiesAccessDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `KeySafeNumber`
  String get pageEntitiesAccessKeySafeNumberField {
    return Intl.message(
      'KeySafeNumber',
      name: 'pageEntitiesAccessKeySafeNumberField',
      desc: '',
      args: [],
    );
  }

  /// `AccessDetails`
  String get pageEntitiesAccessAccessDetailsField {
    return Intl.message(
      'AccessDetails',
      name: 'pageEntitiesAccessAccessDetailsField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesAccessCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesAccessCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesAccessLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesAccessLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesAccessClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesAccessClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesAccessHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesAccessHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Answers list`
  String get pageEntitiesAnswerListTitle {
    return Intl.message(
      'Answers list',
      name: 'pageEntitiesAnswerListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Answers`
  String get pageEntitiesAnswerUpdateTitle {
    return Intl.message(
      'Edit Answers',
      name: 'pageEntitiesAnswerUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Answers`
  String get pageEntitiesAnswerCreateTitle {
    return Intl.message(
      'Create Answers',
      name: 'pageEntitiesAnswerCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Answers View`
  String get pageEntitiesAnswerViewTitle {
    return Intl.message(
      'Answers View',
      name: 'pageEntitiesAnswerViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Answers`
  String get pageEntitiesAnswerDeletePopupTitle {
    return Intl.message(
      'Delete Answers',
      name: 'pageEntitiesAnswerDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Answer deleted successfuly`
  String get pageEntitiesAnswerDeleteOk {
    return Intl.message(
      'Answer deleted successfuly',
      name: 'pageEntitiesAnswerDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Answer`
  String get pageEntitiesAnswerAnswerField {
    return Intl.message(
      'Answer',
      name: 'pageEntitiesAnswerAnswerField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesAnswerDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesAnswerDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `Attribute1`
  String get pageEntitiesAnswerAttribute1Field {
    return Intl.message(
      'Attribute1',
      name: 'pageEntitiesAnswerAttribute1Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute2`
  String get pageEntitiesAnswerAttribute2Field {
    return Intl.message(
      'Attribute2',
      name: 'pageEntitiesAnswerAttribute2Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute3`
  String get pageEntitiesAnswerAttribute3Field {
    return Intl.message(
      'Attribute3',
      name: 'pageEntitiesAnswerAttribute3Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute4`
  String get pageEntitiesAnswerAttribute4Field {
    return Intl.message(
      'Attribute4',
      name: 'pageEntitiesAnswerAttribute4Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute5`
  String get pageEntitiesAnswerAttribute5Field {
    return Intl.message(
      'Attribute5',
      name: 'pageEntitiesAnswerAttribute5Field',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesAnswerCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesAnswerCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesAnswerLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesAnswerLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesAnswerClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesAnswerClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesAnswerHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesAnswerHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Branches list`
  String get pageEntitiesBranchListTitle {
    return Intl.message(
      'Branches list',
      name: 'pageEntitiesBranchListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Branches`
  String get pageEntitiesBranchUpdateTitle {
    return Intl.message(
      'Edit Branches',
      name: 'pageEntitiesBranchUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Branches`
  String get pageEntitiesBranchCreateTitle {
    return Intl.message(
      'Create Branches',
      name: 'pageEntitiesBranchCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Branches View`
  String get pageEntitiesBranchViewTitle {
    return Intl.message(
      'Branches View',
      name: 'pageEntitiesBranchViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Branches`
  String get pageEntitiesBranchDeletePopupTitle {
    return Intl.message(
      'Delete Branches',
      name: 'pageEntitiesBranchDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Branch deleted successfuly`
  String get pageEntitiesBranchDeleteOk {
    return Intl.message(
      'Branch deleted successfuly',
      name: 'pageEntitiesBranchDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Name`
  String get pageEntitiesBranchNameField {
    return Intl.message(
      'Name',
      name: 'pageEntitiesBranchNameField',
      desc: '',
      args: [],
    );
  }

  /// `Address`
  String get pageEntitiesBranchAddressField {
    return Intl.message(
      'Address',
      name: 'pageEntitiesBranchAddressField',
      desc: '',
      args: [],
    );
  }

  /// `Telephone`
  String get pageEntitiesBranchTelephoneField {
    return Intl.message(
      'Telephone',
      name: 'pageEntitiesBranchTelephoneField',
      desc: '',
      args: [],
    );
  }

  /// `ContactName`
  String get pageEntitiesBranchContactNameField {
    return Intl.message(
      'ContactName',
      name: 'pageEntitiesBranchContactNameField',
      desc: '',
      args: [],
    );
  }

  /// `BranchEmail`
  String get pageEntitiesBranchBranchEmailField {
    return Intl.message(
      'BranchEmail',
      name: 'pageEntitiesBranchBranchEmailField',
      desc: '',
      args: [],
    );
  }

  /// `PhotoUrl`
  String get pageEntitiesBranchPhotoUrlField {
    return Intl.message(
      'PhotoUrl',
      name: 'pageEntitiesBranchPhotoUrlField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesBranchCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesBranchCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesBranchLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesBranchLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesBranchHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesBranchHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `CarerServiceUserRelations list`
  String get pageEntitiesCarerServiceUserRelationListTitle {
    return Intl.message(
      'CarerServiceUserRelations list',
      name: 'pageEntitiesCarerServiceUserRelationListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit CarerServiceUserRelations`
  String get pageEntitiesCarerServiceUserRelationUpdateTitle {
    return Intl.message(
      'Edit CarerServiceUserRelations',
      name: 'pageEntitiesCarerServiceUserRelationUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create CarerServiceUserRelations`
  String get pageEntitiesCarerServiceUserRelationCreateTitle {
    return Intl.message(
      'Create CarerServiceUserRelations',
      name: 'pageEntitiesCarerServiceUserRelationCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `CarerServiceUserRelations View`
  String get pageEntitiesCarerServiceUserRelationViewTitle {
    return Intl.message(
      'CarerServiceUserRelations View',
      name: 'pageEntitiesCarerServiceUserRelationViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete CarerServiceUserRelations`
  String get pageEntitiesCarerServiceUserRelationDeletePopupTitle {
    return Intl.message(
      'Delete CarerServiceUserRelations',
      name: 'pageEntitiesCarerServiceUserRelationDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `CarerServiceUserRelation deleted successfuly`
  String get pageEntitiesCarerServiceUserRelationDeleteOk {
    return Intl.message(
      'CarerServiceUserRelation deleted successfuly',
      name: 'pageEntitiesCarerServiceUserRelationDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Reason`
  String get pageEntitiesCarerServiceUserRelationReasonField {
    return Intl.message(
      'Reason',
      name: 'pageEntitiesCarerServiceUserRelationReasonField',
      desc: '',
      args: [],
    );
  }

  /// `Count`
  String get pageEntitiesCarerServiceUserRelationCountField {
    return Intl.message(
      'Count',
      name: 'pageEntitiesCarerServiceUserRelationCountField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesCarerServiceUserRelationCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesCarerServiceUserRelationCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesCarerServiceUserRelationLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesCarerServiceUserRelationLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesCarerServiceUserRelationClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesCarerServiceUserRelationClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesCarerServiceUserRelationHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesCarerServiceUserRelationHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Clients list`
  String get pageEntitiesClientListTitle {
    return Intl.message(
      'Clients list',
      name: 'pageEntitiesClientListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Clients`
  String get pageEntitiesClientUpdateTitle {
    return Intl.message(
      'Edit Clients',
      name: 'pageEntitiesClientUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Clients`
  String get pageEntitiesClientCreateTitle {
    return Intl.message(
      'Create Clients',
      name: 'pageEntitiesClientCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Clients View`
  String get pageEntitiesClientViewTitle {
    return Intl.message(
      'Clients View',
      name: 'pageEntitiesClientViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Clients`
  String get pageEntitiesClientDeletePopupTitle {
    return Intl.message(
      'Delete Clients',
      name: 'pageEntitiesClientDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Client deleted successfuly`
  String get pageEntitiesClientDeleteOk {
    return Intl.message(
      'Client deleted successfuly',
      name: 'pageEntitiesClientDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `ClientName`
  String get pageEntitiesClientClientNameField {
    return Intl.message(
      'ClientName',
      name: 'pageEntitiesClientClientNameField',
      desc: '',
      args: [],
    );
  }

  /// `ClientDescription`
  String get pageEntitiesClientClientDescriptionField {
    return Intl.message(
      'ClientDescription',
      name: 'pageEntitiesClientClientDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `ClientLogoUrl`
  String get pageEntitiesClientClientLogoUrlField {
    return Intl.message(
      'ClientLogoUrl',
      name: 'pageEntitiesClientClientLogoUrlField',
      desc: '',
      args: [],
    );
  }

  /// `ClientContactName`
  String get pageEntitiesClientClientContactNameField {
    return Intl.message(
      'ClientContactName',
      name: 'pageEntitiesClientClientContactNameField',
      desc: '',
      args: [],
    );
  }

  /// `ClientPhone`
  String get pageEntitiesClientClientPhoneField {
    return Intl.message(
      'ClientPhone',
      name: 'pageEntitiesClientClientPhoneField',
      desc: '',
      args: [],
    );
  }

  /// `ClientEmail`
  String get pageEntitiesClientClientEmailField {
    return Intl.message(
      'ClientEmail',
      name: 'pageEntitiesClientClientEmailField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesClientCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesClientCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `Enabled`
  String get pageEntitiesClientEnabledField {
    return Intl.message(
      'Enabled',
      name: 'pageEntitiesClientEnabledField',
      desc: '',
      args: [],
    );
  }

  /// `Reason`
  String get pageEntitiesClientReasonField {
    return Intl.message(
      'Reason',
      name: 'pageEntitiesClientReasonField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesClientLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesClientLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesClientHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesClientHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `ClientDocuments list`
  String get pageEntitiesClientDocumentListTitle {
    return Intl.message(
      'ClientDocuments list',
      name: 'pageEntitiesClientDocumentListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit ClientDocuments`
  String get pageEntitiesClientDocumentUpdateTitle {
    return Intl.message(
      'Edit ClientDocuments',
      name: 'pageEntitiesClientDocumentUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create ClientDocuments`
  String get pageEntitiesClientDocumentCreateTitle {
    return Intl.message(
      'Create ClientDocuments',
      name: 'pageEntitiesClientDocumentCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `ClientDocuments View`
  String get pageEntitiesClientDocumentViewTitle {
    return Intl.message(
      'ClientDocuments View',
      name: 'pageEntitiesClientDocumentViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete ClientDocuments`
  String get pageEntitiesClientDocumentDeletePopupTitle {
    return Intl.message(
      'Delete ClientDocuments',
      name: 'pageEntitiesClientDocumentDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `ClientDocument deleted successfuly`
  String get pageEntitiesClientDocumentDeleteOk {
    return Intl.message(
      'ClientDocument deleted successfuly',
      name: 'pageEntitiesClientDocumentDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `DocumentName`
  String get pageEntitiesClientDocumentDocumentNameField {
    return Intl.message(
      'DocumentName',
      name: 'pageEntitiesClientDocumentDocumentNameField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentNumber`
  String get pageEntitiesClientDocumentDocumentNumberField {
    return Intl.message(
      'DocumentNumber',
      name: 'pageEntitiesClientDocumentDocumentNumberField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentType`
  String get pageEntitiesClientDocumentDocumentTypeField {
    return Intl.message(
      'DocumentType',
      name: 'pageEntitiesClientDocumentDocumentTypeField',
      desc: '',
      args: [],
    );
  }

  /// `ClientDocumentStatus`
  String get pageEntitiesClientDocumentClientDocumentStatusField {
    return Intl.message(
      'ClientDocumentStatus',
      name: 'pageEntitiesClientDocumentClientDocumentStatusField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesClientDocumentNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesClientDocumentNoteField',
      desc: '',
      args: [],
    );
  }

  /// `IssuedDate`
  String get pageEntitiesClientDocumentIssuedDateField {
    return Intl.message(
      'IssuedDate',
      name: 'pageEntitiesClientDocumentIssuedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ExpiryDate`
  String get pageEntitiesClientDocumentExpiryDateField {
    return Intl.message(
      'ExpiryDate',
      name: 'pageEntitiesClientDocumentExpiryDateField',
      desc: '',
      args: [],
    );
  }

  /// `UploadedDate`
  String get pageEntitiesClientDocumentUploadedDateField {
    return Intl.message(
      'UploadedDate',
      name: 'pageEntitiesClientDocumentUploadedDateField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentFileUrl`
  String get pageEntitiesClientDocumentDocumentFileUrlField {
    return Intl.message(
      'DocumentFileUrl',
      name: 'pageEntitiesClientDocumentDocumentFileUrlField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesClientDocumentCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesClientDocumentCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesClientDocumentLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesClientDocumentLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesClientDocumentClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesClientDocumentClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesClientDocumentHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesClientDocumentHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Communications list`
  String get pageEntitiesCommunicationListTitle {
    return Intl.message(
      'Communications list',
      name: 'pageEntitiesCommunicationListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Communications`
  String get pageEntitiesCommunicationUpdateTitle {
    return Intl.message(
      'Edit Communications',
      name: 'pageEntitiesCommunicationUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Communications`
  String get pageEntitiesCommunicationCreateTitle {
    return Intl.message(
      'Create Communications',
      name: 'pageEntitiesCommunicationCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Communications View`
  String get pageEntitiesCommunicationViewTitle {
    return Intl.message(
      'Communications View',
      name: 'pageEntitiesCommunicationViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Communications`
  String get pageEntitiesCommunicationDeletePopupTitle {
    return Intl.message(
      'Delete Communications',
      name: 'pageEntitiesCommunicationDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Communication deleted successfuly`
  String get pageEntitiesCommunicationDeleteOk {
    return Intl.message(
      'Communication deleted successfuly',
      name: 'pageEntitiesCommunicationDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `CommunicationType`
  String get pageEntitiesCommunicationCommunicationTypeField {
    return Intl.message(
      'CommunicationType',
      name: 'pageEntitiesCommunicationCommunicationTypeField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesCommunicationNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesCommunicationNoteField',
      desc: '',
      args: [],
    );
  }

  /// `CommunicationDate`
  String get pageEntitiesCommunicationCommunicationDateField {
    return Intl.message(
      'CommunicationDate',
      name: 'pageEntitiesCommunicationCommunicationDateField',
      desc: '',
      args: [],
    );
  }

  /// `AttachmentUrl`
  String get pageEntitiesCommunicationAttachmentUrlField {
    return Intl.message(
      'AttachmentUrl',
      name: 'pageEntitiesCommunicationAttachmentUrlField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesCommunicationCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesCommunicationCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesCommunicationLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesCommunicationLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesCommunicationClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesCommunicationClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesCommunicationHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesCommunicationHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Consents list`
  String get pageEntitiesConsentListTitle {
    return Intl.message(
      'Consents list',
      name: 'pageEntitiesConsentListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Consents`
  String get pageEntitiesConsentUpdateTitle {
    return Intl.message(
      'Edit Consents',
      name: 'pageEntitiesConsentUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Consents`
  String get pageEntitiesConsentCreateTitle {
    return Intl.message(
      'Create Consents',
      name: 'pageEntitiesConsentCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Consents View`
  String get pageEntitiesConsentViewTitle {
    return Intl.message(
      'Consents View',
      name: 'pageEntitiesConsentViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Consents`
  String get pageEntitiesConsentDeletePopupTitle {
    return Intl.message(
      'Delete Consents',
      name: 'pageEntitiesConsentDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Consent deleted successfuly`
  String get pageEntitiesConsentDeleteOk {
    return Intl.message(
      'Consent deleted successfuly',
      name: 'pageEntitiesConsentDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Title`
  String get pageEntitiesConsentTitleField {
    return Intl.message(
      'Title',
      name: 'pageEntitiesConsentTitleField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesConsentDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesConsentDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `GiveConsent`
  String get pageEntitiesConsentGiveConsentField {
    return Intl.message(
      'GiveConsent',
      name: 'pageEntitiesConsentGiveConsentField',
      desc: '',
      args: [],
    );
  }

  /// `Arrangements`
  String get pageEntitiesConsentArrangementsField {
    return Intl.message(
      'Arrangements',
      name: 'pageEntitiesConsentArrangementsField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserSignature`
  String get pageEntitiesConsentServiceUserSignatureField {
    return Intl.message(
      'ServiceUserSignature',
      name: 'pageEntitiesConsentServiceUserSignatureField',
      desc: '',
      args: [],
    );
  }

  /// `SignatureImageUrl`
  String get pageEntitiesConsentSignatureImageUrlField {
    return Intl.message(
      'SignatureImageUrl',
      name: 'pageEntitiesConsentSignatureImageUrlField',
      desc: '',
      args: [],
    );
  }

  /// `ConsentDate`
  String get pageEntitiesConsentConsentDateField {
    return Intl.message(
      'ConsentDate',
      name: 'pageEntitiesConsentConsentDateField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesConsentCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesConsentCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesConsentLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesConsentLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesConsentClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesConsentClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesConsentHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesConsentHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Countries list`
  String get pageEntitiesCountryListTitle {
    return Intl.message(
      'Countries list',
      name: 'pageEntitiesCountryListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Countries`
  String get pageEntitiesCountryUpdateTitle {
    return Intl.message(
      'Edit Countries',
      name: 'pageEntitiesCountryUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Countries`
  String get pageEntitiesCountryCreateTitle {
    return Intl.message(
      'Create Countries',
      name: 'pageEntitiesCountryCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Countries View`
  String get pageEntitiesCountryViewTitle {
    return Intl.message(
      'Countries View',
      name: 'pageEntitiesCountryViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Countries`
  String get pageEntitiesCountryDeletePopupTitle {
    return Intl.message(
      'Delete Countries',
      name: 'pageEntitiesCountryDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Country deleted successfuly`
  String get pageEntitiesCountryDeleteOk {
    return Intl.message(
      'Country deleted successfuly',
      name: 'pageEntitiesCountryDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `CountryName`
  String get pageEntitiesCountryCountryNameField {
    return Intl.message(
      'CountryName',
      name: 'pageEntitiesCountryCountryNameField',
      desc: '',
      args: [],
    );
  }

  /// `CountryIsoCode`
  String get pageEntitiesCountryCountryIsoCodeField {
    return Intl.message(
      'CountryIsoCode',
      name: 'pageEntitiesCountryCountryIsoCodeField',
      desc: '',
      args: [],
    );
  }

  /// `CountryFlagUrl`
  String get pageEntitiesCountryCountryFlagUrlField {
    return Intl.message(
      'CountryFlagUrl',
      name: 'pageEntitiesCountryCountryFlagUrlField',
      desc: '',
      args: [],
    );
  }

  /// `CountryCallingCode`
  String get pageEntitiesCountryCountryCallingCodeField {
    return Intl.message(
      'CountryCallingCode',
      name: 'pageEntitiesCountryCountryCallingCodeField',
      desc: '',
      args: [],
    );
  }

  /// `CountryTelDigitLength`
  String get pageEntitiesCountryCountryTelDigitLengthField {
    return Intl.message(
      'CountryTelDigitLength',
      name: 'pageEntitiesCountryCountryTelDigitLengthField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesCountryCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesCountryCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesCountryLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesCountryLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesCountryHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesCountryHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Currencies list`
  String get pageEntitiesCurrencyListTitle {
    return Intl.message(
      'Currencies list',
      name: 'pageEntitiesCurrencyListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Currencies`
  String get pageEntitiesCurrencyUpdateTitle {
    return Intl.message(
      'Edit Currencies',
      name: 'pageEntitiesCurrencyUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Currencies`
  String get pageEntitiesCurrencyCreateTitle {
    return Intl.message(
      'Create Currencies',
      name: 'pageEntitiesCurrencyCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Currencies View`
  String get pageEntitiesCurrencyViewTitle {
    return Intl.message(
      'Currencies View',
      name: 'pageEntitiesCurrencyViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Currencies`
  String get pageEntitiesCurrencyDeletePopupTitle {
    return Intl.message(
      'Delete Currencies',
      name: 'pageEntitiesCurrencyDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Currency deleted successfuly`
  String get pageEntitiesCurrencyDeleteOk {
    return Intl.message(
      'Currency deleted successfuly',
      name: 'pageEntitiesCurrencyDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Currency`
  String get pageEntitiesCurrencyCurrencyField {
    return Intl.message(
      'Currency',
      name: 'pageEntitiesCurrencyCurrencyField',
      desc: '',
      args: [],
    );
  }

  /// `CurrencyIsoCode`
  String get pageEntitiesCurrencyCurrencyIsoCodeField {
    return Intl.message(
      'CurrencyIsoCode',
      name: 'pageEntitiesCurrencyCurrencyIsoCodeField',
      desc: '',
      args: [],
    );
  }

  /// `CurrencySymbol`
  String get pageEntitiesCurrencyCurrencySymbolField {
    return Intl.message(
      'CurrencySymbol',
      name: 'pageEntitiesCurrencyCurrencySymbolField',
      desc: '',
      args: [],
    );
  }

  /// `CurrencyLogoUrl`
  String get pageEntitiesCurrencyCurrencyLogoUrlField {
    return Intl.message(
      'CurrencyLogoUrl',
      name: 'pageEntitiesCurrencyCurrencyLogoUrlField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesCurrencyHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesCurrencyHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Disabilities list`
  String get pageEntitiesDisabilityListTitle {
    return Intl.message(
      'Disabilities list',
      name: 'pageEntitiesDisabilityListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Disabilities`
  String get pageEntitiesDisabilityUpdateTitle {
    return Intl.message(
      'Edit Disabilities',
      name: 'pageEntitiesDisabilityUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Disabilities`
  String get pageEntitiesDisabilityCreateTitle {
    return Intl.message(
      'Create Disabilities',
      name: 'pageEntitiesDisabilityCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Disabilities View`
  String get pageEntitiesDisabilityViewTitle {
    return Intl.message(
      'Disabilities View',
      name: 'pageEntitiesDisabilityViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Disabilities`
  String get pageEntitiesDisabilityDeletePopupTitle {
    return Intl.message(
      'Delete Disabilities',
      name: 'pageEntitiesDisabilityDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Disability deleted successfuly`
  String get pageEntitiesDisabilityDeleteOk {
    return Intl.message(
      'Disability deleted successfuly',
      name: 'pageEntitiesDisabilityDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `IsDisabled`
  String get pageEntitiesDisabilityIsDisabledField {
    return Intl.message(
      'IsDisabled',
      name: 'pageEntitiesDisabilityIsDisabledField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesDisabilityNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesDisabilityNoteField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesDisabilityCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesDisabilityCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesDisabilityLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesDisabilityLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesDisabilityClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesDisabilityClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesDisabilityHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesDisabilityHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `DisabilityTypes list`
  String get pageEntitiesDisabilityTypeListTitle {
    return Intl.message(
      'DisabilityTypes list',
      name: 'pageEntitiesDisabilityTypeListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit DisabilityTypes`
  String get pageEntitiesDisabilityTypeUpdateTitle {
    return Intl.message(
      'Edit DisabilityTypes',
      name: 'pageEntitiesDisabilityTypeUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create DisabilityTypes`
  String get pageEntitiesDisabilityTypeCreateTitle {
    return Intl.message(
      'Create DisabilityTypes',
      name: 'pageEntitiesDisabilityTypeCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `DisabilityTypes View`
  String get pageEntitiesDisabilityTypeViewTitle {
    return Intl.message(
      'DisabilityTypes View',
      name: 'pageEntitiesDisabilityTypeViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete DisabilityTypes`
  String get pageEntitiesDisabilityTypeDeletePopupTitle {
    return Intl.message(
      'Delete DisabilityTypes',
      name: 'pageEntitiesDisabilityTypeDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `DisabilityType deleted successfuly`
  String get pageEntitiesDisabilityTypeDeleteOk {
    return Intl.message(
      'DisabilityType deleted successfuly',
      name: 'pageEntitiesDisabilityTypeDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Disability`
  String get pageEntitiesDisabilityTypeDisabilityField {
    return Intl.message(
      'Disability',
      name: 'pageEntitiesDisabilityTypeDisabilityField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesDisabilityTypeDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesDisabilityTypeDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesDisabilityTypeHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesDisabilityTypeHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentTypes list`
  String get pageEntitiesDocumentTypeListTitle {
    return Intl.message(
      'DocumentTypes list',
      name: 'pageEntitiesDocumentTypeListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit DocumentTypes`
  String get pageEntitiesDocumentTypeUpdateTitle {
    return Intl.message(
      'Edit DocumentTypes',
      name: 'pageEntitiesDocumentTypeUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create DocumentTypes`
  String get pageEntitiesDocumentTypeCreateTitle {
    return Intl.message(
      'Create DocumentTypes',
      name: 'pageEntitiesDocumentTypeCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `DocumentTypes View`
  String get pageEntitiesDocumentTypeViewTitle {
    return Intl.message(
      'DocumentTypes View',
      name: 'pageEntitiesDocumentTypeViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete DocumentTypes`
  String get pageEntitiesDocumentTypeDeletePopupTitle {
    return Intl.message(
      'Delete DocumentTypes',
      name: 'pageEntitiesDocumentTypeDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `DocumentType deleted successfuly`
  String get pageEntitiesDocumentTypeDeleteOk {
    return Intl.message(
      'DocumentType deleted successfuly',
      name: 'pageEntitiesDocumentTypeDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `DocumentTypeTitle`
  String get pageEntitiesDocumentTypeDocumentTypeTitleField {
    return Intl.message(
      'DocumentTypeTitle',
      name: 'pageEntitiesDocumentTypeDocumentTypeTitleField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentTypeDescription`
  String get pageEntitiesDocumentTypeDocumentTypeDescriptionField {
    return Intl.message(
      'DocumentTypeDescription',
      name: 'pageEntitiesDocumentTypeDocumentTypeDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesDocumentTypeCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesDocumentTypeCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesDocumentTypeLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesDocumentTypeLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesDocumentTypeHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesDocumentTypeHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Eligibilities list`
  String get pageEntitiesEligibilityListTitle {
    return Intl.message(
      'Eligibilities list',
      name: 'pageEntitiesEligibilityListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Eligibilities`
  String get pageEntitiesEligibilityUpdateTitle {
    return Intl.message(
      'Edit Eligibilities',
      name: 'pageEntitiesEligibilityUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Eligibilities`
  String get pageEntitiesEligibilityCreateTitle {
    return Intl.message(
      'Create Eligibilities',
      name: 'pageEntitiesEligibilityCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Eligibilities View`
  String get pageEntitiesEligibilityViewTitle {
    return Intl.message(
      'Eligibilities View',
      name: 'pageEntitiesEligibilityViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Eligibilities`
  String get pageEntitiesEligibilityDeletePopupTitle {
    return Intl.message(
      'Delete Eligibilities',
      name: 'pageEntitiesEligibilityDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Eligibility deleted successfuly`
  String get pageEntitiesEligibilityDeleteOk {
    return Intl.message(
      'Eligibility deleted successfuly',
      name: 'pageEntitiesEligibilityDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `IsEligible`
  String get pageEntitiesEligibilityIsEligibleField {
    return Intl.message(
      'IsEligible',
      name: 'pageEntitiesEligibilityIsEligibleField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesEligibilityNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesEligibilityNoteField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesEligibilityCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesEligibilityCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesEligibilityLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesEligibilityLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesEligibilityClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesEligibilityClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEligibilityHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEligibilityHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `EligibilityTypes list`
  String get pageEntitiesEligibilityTypeListTitle {
    return Intl.message(
      'EligibilityTypes list',
      name: 'pageEntitiesEligibilityTypeListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit EligibilityTypes`
  String get pageEntitiesEligibilityTypeUpdateTitle {
    return Intl.message(
      'Edit EligibilityTypes',
      name: 'pageEntitiesEligibilityTypeUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create EligibilityTypes`
  String get pageEntitiesEligibilityTypeCreateTitle {
    return Intl.message(
      'Create EligibilityTypes',
      name: 'pageEntitiesEligibilityTypeCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `EligibilityTypes View`
  String get pageEntitiesEligibilityTypeViewTitle {
    return Intl.message(
      'EligibilityTypes View',
      name: 'pageEntitiesEligibilityTypeViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete EligibilityTypes`
  String get pageEntitiesEligibilityTypeDeletePopupTitle {
    return Intl.message(
      'Delete EligibilityTypes',
      name: 'pageEntitiesEligibilityTypeDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `EligibilityType deleted successfuly`
  String get pageEntitiesEligibilityTypeDeleteOk {
    return Intl.message(
      'EligibilityType deleted successfuly',
      name: 'pageEntitiesEligibilityTypeDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `EligibilityType`
  String get pageEntitiesEligibilityTypeEligibilityTypeField {
    return Intl.message(
      'EligibilityType',
      name: 'pageEntitiesEligibilityTypeEligibilityTypeField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesEligibilityTypeDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesEligibilityTypeDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEligibilityTypeHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEligibilityTypeHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `EmergencyContacts list`
  String get pageEntitiesEmergencyContactListTitle {
    return Intl.message(
      'EmergencyContacts list',
      name: 'pageEntitiesEmergencyContactListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit EmergencyContacts`
  String get pageEntitiesEmergencyContactUpdateTitle {
    return Intl.message(
      'Edit EmergencyContacts',
      name: 'pageEntitiesEmergencyContactUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create EmergencyContacts`
  String get pageEntitiesEmergencyContactCreateTitle {
    return Intl.message(
      'Create EmergencyContacts',
      name: 'pageEntitiesEmergencyContactCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmergencyContacts View`
  String get pageEntitiesEmergencyContactViewTitle {
    return Intl.message(
      'EmergencyContacts View',
      name: 'pageEntitiesEmergencyContactViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete EmergencyContacts`
  String get pageEntitiesEmergencyContactDeletePopupTitle {
    return Intl.message(
      'Delete EmergencyContacts',
      name: 'pageEntitiesEmergencyContactDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmergencyContact deleted successfuly`
  String get pageEntitiesEmergencyContactDeleteOk {
    return Intl.message(
      'EmergencyContact deleted successfuly',
      name: 'pageEntitiesEmergencyContactDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Name`
  String get pageEntitiesEmergencyContactNameField {
    return Intl.message(
      'Name',
      name: 'pageEntitiesEmergencyContactNameField',
      desc: '',
      args: [],
    );
  }

  /// `ContactRelationship`
  String get pageEntitiesEmergencyContactContactRelationshipField {
    return Intl.message(
      'ContactRelationship',
      name: 'pageEntitiesEmergencyContactContactRelationshipField',
      desc: '',
      args: [],
    );
  }

  /// `IsKeyHolder`
  String get pageEntitiesEmergencyContactIsKeyHolderField {
    return Intl.message(
      'IsKeyHolder',
      name: 'pageEntitiesEmergencyContactIsKeyHolderField',
      desc: '',
      args: [],
    );
  }

  /// `InfoSharingConsentGiven`
  String get pageEntitiesEmergencyContactInfoSharingConsentGivenField {
    return Intl.message(
      'InfoSharingConsentGiven',
      name: 'pageEntitiesEmergencyContactInfoSharingConsentGivenField',
      desc: '',
      args: [],
    );
  }

  /// `PreferredContactNumber`
  String get pageEntitiesEmergencyContactPreferredContactNumberField {
    return Intl.message(
      'PreferredContactNumber',
      name: 'pageEntitiesEmergencyContactPreferredContactNumberField',
      desc: '',
      args: [],
    );
  }

  /// `FullAddress`
  String get pageEntitiesEmergencyContactFullAddressField {
    return Intl.message(
      'FullAddress',
      name: 'pageEntitiesEmergencyContactFullAddressField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesEmergencyContactCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesEmergencyContactCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesEmergencyContactLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesEmergencyContactLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesEmergencyContactClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesEmergencyContactClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEmergencyContactHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEmergencyContactHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Employees list`
  String get pageEntitiesEmployeeListTitle {
    return Intl.message(
      'Employees list',
      name: 'pageEntitiesEmployeeListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Employees`
  String get pageEntitiesEmployeeUpdateTitle {
    return Intl.message(
      'Edit Employees',
      name: 'pageEntitiesEmployeeUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Employees`
  String get pageEntitiesEmployeeCreateTitle {
    return Intl.message(
      'Create Employees',
      name: 'pageEntitiesEmployeeCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Employees View`
  String get pageEntitiesEmployeeViewTitle {
    return Intl.message(
      'Employees View',
      name: 'pageEntitiesEmployeeViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Employees`
  String get pageEntitiesEmployeeDeletePopupTitle {
    return Intl.message(
      'Delete Employees',
      name: 'pageEntitiesEmployeeDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Employee deleted successfuly`
  String get pageEntitiesEmployeeDeleteOk {
    return Intl.message(
      'Employee deleted successfuly',
      name: 'pageEntitiesEmployeeDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Tittle`
  String get pageEntitiesEmployeeTittleField {
    return Intl.message(
      'Tittle',
      name: 'pageEntitiesEmployeeTittleField',
      desc: '',
      args: [],
    );
  }

  /// `FirstName`
  String get pageEntitiesEmployeeFirstNameField {
    return Intl.message(
      'FirstName',
      name: 'pageEntitiesEmployeeFirstNameField',
      desc: '',
      args: [],
    );
  }

  /// `MiddleInitial`
  String get pageEntitiesEmployeeMiddleInitialField {
    return Intl.message(
      'MiddleInitial',
      name: 'pageEntitiesEmployeeMiddleInitialField',
      desc: '',
      args: [],
    );
  }

  /// `LastName`
  String get pageEntitiesEmployeeLastNameField {
    return Intl.message(
      'LastName',
      name: 'pageEntitiesEmployeeLastNameField',
      desc: '',
      args: [],
    );
  }

  /// `PreferredName`
  String get pageEntitiesEmployeePreferredNameField {
    return Intl.message(
      'PreferredName',
      name: 'pageEntitiesEmployeePreferredNameField',
      desc: '',
      args: [],
    );
  }

  /// `Gendder`
  String get pageEntitiesEmployeeGendderField {
    return Intl.message(
      'Gendder',
      name: 'pageEntitiesEmployeeGendderField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeCode`
  String get pageEntitiesEmployeeEmployeeCodeField {
    return Intl.message(
      'EmployeeCode',
      name: 'pageEntitiesEmployeeEmployeeCodeField',
      desc: '',
      args: [],
    );
  }

  /// `Phone`
  String get pageEntitiesEmployeePhoneField {
    return Intl.message(
      'Phone',
      name: 'pageEntitiesEmployeePhoneField',
      desc: '',
      args: [],
    );
  }

  /// `Email`
  String get pageEntitiesEmployeeEmailField {
    return Intl.message(
      'Email',
      name: 'pageEntitiesEmployeeEmailField',
      desc: '',
      args: [],
    );
  }

  /// `NationalInsuranceNumber`
  String get pageEntitiesEmployeeNationalInsuranceNumberField {
    return Intl.message(
      'NationalInsuranceNumber',
      name: 'pageEntitiesEmployeeNationalInsuranceNumberField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeContractType`
  String get pageEntitiesEmployeeEmployeeContractTypeField {
    return Intl.message(
      'EmployeeContractType',
      name: 'pageEntitiesEmployeeEmployeeContractTypeField',
      desc: '',
      args: [],
    );
  }

  /// `PinCode`
  String get pageEntitiesEmployeePinCodeField {
    return Intl.message(
      'PinCode',
      name: 'pageEntitiesEmployeePinCodeField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeTransportMode`
  String get pageEntitiesEmployeeEmployeeTransportModeField {
    return Intl.message(
      'EmployeeTransportMode',
      name: 'pageEntitiesEmployeeEmployeeTransportModeField',
      desc: '',
      args: [],
    );
  }

  /// `Address`
  String get pageEntitiesEmployeeAddressField {
    return Intl.message(
      'Address',
      name: 'pageEntitiesEmployeeAddressField',
      desc: '',
      args: [],
    );
  }

  /// `County`
  String get pageEntitiesEmployeeCountyField {
    return Intl.message(
      'County',
      name: 'pageEntitiesEmployeeCountyField',
      desc: '',
      args: [],
    );
  }

  /// `PostCode`
  String get pageEntitiesEmployeePostCodeField {
    return Intl.message(
      'PostCode',
      name: 'pageEntitiesEmployeePostCodeField',
      desc: '',
      args: [],
    );
  }

  /// `DateOfBirth`
  String get pageEntitiesEmployeeDateOfBirthField {
    return Intl.message(
      'DateOfBirth',
      name: 'pageEntitiesEmployeeDateOfBirthField',
      desc: '',
      args: [],
    );
  }

  /// `PhotoUrl`
  String get pageEntitiesEmployeePhotoUrlField {
    return Intl.message(
      'PhotoUrl',
      name: 'pageEntitiesEmployeePhotoUrlField',
      desc: '',
      args: [],
    );
  }

  /// `Status`
  String get pageEntitiesEmployeeStatusField {
    return Intl.message(
      'Status',
      name: 'pageEntitiesEmployeeStatusField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeBio`
  String get pageEntitiesEmployeeEmployeeBioField {
    return Intl.message(
      'EmployeeBio',
      name: 'pageEntitiesEmployeeEmployeeBioField',
      desc: '',
      args: [],
    );
  }

  /// `AcruedHolidayHours`
  String get pageEntitiesEmployeeAcruedHolidayHoursField {
    return Intl.message(
      'AcruedHolidayHours',
      name: 'pageEntitiesEmployeeAcruedHolidayHoursField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesEmployeeCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesEmployeeCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesEmployeeLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesEmployeeLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesEmployeeClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesEmployeeClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEmployeeHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEmployeeHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeAvailabilities list`
  String get pageEntitiesEmployeeAvailabilityListTitle {
    return Intl.message(
      'EmployeeAvailabilities list',
      name: 'pageEntitiesEmployeeAvailabilityListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit EmployeeAvailabilities`
  String get pageEntitiesEmployeeAvailabilityUpdateTitle {
    return Intl.message(
      'Edit EmployeeAvailabilities',
      name: 'pageEntitiesEmployeeAvailabilityUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create EmployeeAvailabilities`
  String get pageEntitiesEmployeeAvailabilityCreateTitle {
    return Intl.message(
      'Create EmployeeAvailabilities',
      name: 'pageEntitiesEmployeeAvailabilityCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeAvailabilities View`
  String get pageEntitiesEmployeeAvailabilityViewTitle {
    return Intl.message(
      'EmployeeAvailabilities View',
      name: 'pageEntitiesEmployeeAvailabilityViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete EmployeeAvailabilities`
  String get pageEntitiesEmployeeAvailabilityDeletePopupTitle {
    return Intl.message(
      'Delete EmployeeAvailabilities',
      name: 'pageEntitiesEmployeeAvailabilityDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeAvailability deleted successfuly`
  String get pageEntitiesEmployeeAvailabilityDeleteOk {
    return Intl.message(
      'EmployeeAvailability deleted successfuly',
      name: 'pageEntitiesEmployeeAvailabilityDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `AvailableForWork`
  String get pageEntitiesEmployeeAvailabilityAvailableForWorkField {
    return Intl.message(
      'AvailableForWork',
      name: 'pageEntitiesEmployeeAvailabilityAvailableForWorkField',
      desc: '',
      args: [],
    );
  }

  /// `AvailableMonday`
  String get pageEntitiesEmployeeAvailabilityAvailableMondayField {
    return Intl.message(
      'AvailableMonday',
      name: 'pageEntitiesEmployeeAvailabilityAvailableMondayField',
      desc: '',
      args: [],
    );
  }

  /// `AvailableTuesday`
  String get pageEntitiesEmployeeAvailabilityAvailableTuesdayField {
    return Intl.message(
      'AvailableTuesday',
      name: 'pageEntitiesEmployeeAvailabilityAvailableTuesdayField',
      desc: '',
      args: [],
    );
  }

  /// `AvailableWednesday`
  String get pageEntitiesEmployeeAvailabilityAvailableWednesdayField {
    return Intl.message(
      'AvailableWednesday',
      name: 'pageEntitiesEmployeeAvailabilityAvailableWednesdayField',
      desc: '',
      args: [],
    );
  }

  /// `AvailableThursday`
  String get pageEntitiesEmployeeAvailabilityAvailableThursdayField {
    return Intl.message(
      'AvailableThursday',
      name: 'pageEntitiesEmployeeAvailabilityAvailableThursdayField',
      desc: '',
      args: [],
    );
  }

  /// `AvailableFriday`
  String get pageEntitiesEmployeeAvailabilityAvailableFridayField {
    return Intl.message(
      'AvailableFriday',
      name: 'pageEntitiesEmployeeAvailabilityAvailableFridayField',
      desc: '',
      args: [],
    );
  }

  /// `AvailableSaturday`
  String get pageEntitiesEmployeeAvailabilityAvailableSaturdayField {
    return Intl.message(
      'AvailableSaturday',
      name: 'pageEntitiesEmployeeAvailabilityAvailableSaturdayField',
      desc: '',
      args: [],
    );
  }

  /// `AvailableSunday`
  String get pageEntitiesEmployeeAvailabilityAvailableSundayField {
    return Intl.message(
      'AvailableSunday',
      name: 'pageEntitiesEmployeeAvailabilityAvailableSundayField',
      desc: '',
      args: [],
    );
  }

  /// `PreferredShift`
  String get pageEntitiesEmployeeAvailabilityPreferredShiftField {
    return Intl.message(
      'PreferredShift',
      name: 'pageEntitiesEmployeeAvailabilityPreferredShiftField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEmployeeAvailabilityHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEmployeeAvailabilityHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesEmployeeAvailabilityLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesEmployeeAvailabilityLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesEmployeeAvailabilityClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesEmployeeAvailabilityClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeDesignations list`
  String get pageEntitiesEmployeeDesignationListTitle {
    return Intl.message(
      'EmployeeDesignations list',
      name: 'pageEntitiesEmployeeDesignationListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit EmployeeDesignations`
  String get pageEntitiesEmployeeDesignationUpdateTitle {
    return Intl.message(
      'Edit EmployeeDesignations',
      name: 'pageEntitiesEmployeeDesignationUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create EmployeeDesignations`
  String get pageEntitiesEmployeeDesignationCreateTitle {
    return Intl.message(
      'Create EmployeeDesignations',
      name: 'pageEntitiesEmployeeDesignationCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeDesignations View`
  String get pageEntitiesEmployeeDesignationViewTitle {
    return Intl.message(
      'EmployeeDesignations View',
      name: 'pageEntitiesEmployeeDesignationViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete EmployeeDesignations`
  String get pageEntitiesEmployeeDesignationDeletePopupTitle {
    return Intl.message(
      'Delete EmployeeDesignations',
      name: 'pageEntitiesEmployeeDesignationDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeDesignation deleted successfuly`
  String get pageEntitiesEmployeeDesignationDeleteOk {
    return Intl.message(
      'EmployeeDesignation deleted successfuly',
      name: 'pageEntitiesEmployeeDesignationDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Designation`
  String get pageEntitiesEmployeeDesignationDesignationField {
    return Intl.message(
      'Designation',
      name: 'pageEntitiesEmployeeDesignationDesignationField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesEmployeeDesignationDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesEmployeeDesignationDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `DesignationDate`
  String get pageEntitiesEmployeeDesignationDesignationDateField {
    return Intl.message(
      'DesignationDate',
      name: 'pageEntitiesEmployeeDesignationDesignationDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesEmployeeDesignationClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesEmployeeDesignationClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEmployeeDesignationHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEmployeeDesignationHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeDocuments list`
  String get pageEntitiesEmployeeDocumentListTitle {
    return Intl.message(
      'EmployeeDocuments list',
      name: 'pageEntitiesEmployeeDocumentListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit EmployeeDocuments`
  String get pageEntitiesEmployeeDocumentUpdateTitle {
    return Intl.message(
      'Edit EmployeeDocuments',
      name: 'pageEntitiesEmployeeDocumentUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create EmployeeDocuments`
  String get pageEntitiesEmployeeDocumentCreateTitle {
    return Intl.message(
      'Create EmployeeDocuments',
      name: 'pageEntitiesEmployeeDocumentCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeDocuments View`
  String get pageEntitiesEmployeeDocumentViewTitle {
    return Intl.message(
      'EmployeeDocuments View',
      name: 'pageEntitiesEmployeeDocumentViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete EmployeeDocuments`
  String get pageEntitiesEmployeeDocumentDeletePopupTitle {
    return Intl.message(
      'Delete EmployeeDocuments',
      name: 'pageEntitiesEmployeeDocumentDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeDocument deleted successfuly`
  String get pageEntitiesEmployeeDocumentDeleteOk {
    return Intl.message(
      'EmployeeDocument deleted successfuly',
      name: 'pageEntitiesEmployeeDocumentDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `DocumentName`
  String get pageEntitiesEmployeeDocumentDocumentNameField {
    return Intl.message(
      'DocumentName',
      name: 'pageEntitiesEmployeeDocumentDocumentNameField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentNumber`
  String get pageEntitiesEmployeeDocumentDocumentNumberField {
    return Intl.message(
      'DocumentNumber',
      name: 'pageEntitiesEmployeeDocumentDocumentNumberField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeDocumentStatus`
  String get pageEntitiesEmployeeDocumentEmployeeDocumentStatusField {
    return Intl.message(
      'EmployeeDocumentStatus',
      name: 'pageEntitiesEmployeeDocumentEmployeeDocumentStatusField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesEmployeeDocumentNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesEmployeeDocumentNoteField',
      desc: '',
      args: [],
    );
  }

  /// `IssuedDate`
  String get pageEntitiesEmployeeDocumentIssuedDateField {
    return Intl.message(
      'IssuedDate',
      name: 'pageEntitiesEmployeeDocumentIssuedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ExpiryDate`
  String get pageEntitiesEmployeeDocumentExpiryDateField {
    return Intl.message(
      'ExpiryDate',
      name: 'pageEntitiesEmployeeDocumentExpiryDateField',
      desc: '',
      args: [],
    );
  }

  /// `UploadedDate`
  String get pageEntitiesEmployeeDocumentUploadedDateField {
    return Intl.message(
      'UploadedDate',
      name: 'pageEntitiesEmployeeDocumentUploadedDateField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentFileUrl`
  String get pageEntitiesEmployeeDocumentDocumentFileUrlField {
    return Intl.message(
      'DocumentFileUrl',
      name: 'pageEntitiesEmployeeDocumentDocumentFileUrlField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesEmployeeDocumentCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesEmployeeDocumentCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesEmployeeDocumentLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesEmployeeDocumentLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesEmployeeDocumentClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesEmployeeDocumentClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEmployeeDocumentHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEmployeeDocumentHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeHolidays list`
  String get pageEntitiesEmployeeHolidayListTitle {
    return Intl.message(
      'EmployeeHolidays list',
      name: 'pageEntitiesEmployeeHolidayListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit EmployeeHolidays`
  String get pageEntitiesEmployeeHolidayUpdateTitle {
    return Intl.message(
      'Edit EmployeeHolidays',
      name: 'pageEntitiesEmployeeHolidayUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create EmployeeHolidays`
  String get pageEntitiesEmployeeHolidayCreateTitle {
    return Intl.message(
      'Create EmployeeHolidays',
      name: 'pageEntitiesEmployeeHolidayCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeHolidays View`
  String get pageEntitiesEmployeeHolidayViewTitle {
    return Intl.message(
      'EmployeeHolidays View',
      name: 'pageEntitiesEmployeeHolidayViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete EmployeeHolidays`
  String get pageEntitiesEmployeeHolidayDeletePopupTitle {
    return Intl.message(
      'Delete EmployeeHolidays',
      name: 'pageEntitiesEmployeeHolidayDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeHoliday deleted successfuly`
  String get pageEntitiesEmployeeHolidayDeleteOk {
    return Intl.message(
      'EmployeeHoliday deleted successfuly',
      name: 'pageEntitiesEmployeeHolidayDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesEmployeeHolidayDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesEmployeeHolidayDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `StartDate`
  String get pageEntitiesEmployeeHolidayStartDateField {
    return Intl.message(
      'StartDate',
      name: 'pageEntitiesEmployeeHolidayStartDateField',
      desc: '',
      args: [],
    );
  }

  /// `EndDate`
  String get pageEntitiesEmployeeHolidayEndDateField {
    return Intl.message(
      'EndDate',
      name: 'pageEntitiesEmployeeHolidayEndDateField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeHolidayType`
  String get pageEntitiesEmployeeHolidayEmployeeHolidayTypeField {
    return Intl.message(
      'EmployeeHolidayType',
      name: 'pageEntitiesEmployeeHolidayEmployeeHolidayTypeField',
      desc: '',
      args: [],
    );
  }

  /// `ApprovedDate`
  String get pageEntitiesEmployeeHolidayApprovedDateField {
    return Intl.message(
      'ApprovedDate',
      name: 'pageEntitiesEmployeeHolidayApprovedDateField',
      desc: '',
      args: [],
    );
  }

  /// `RequestedDate`
  String get pageEntitiesEmployeeHolidayRequestedDateField {
    return Intl.message(
      'RequestedDate',
      name: 'pageEntitiesEmployeeHolidayRequestedDateField',
      desc: '',
      args: [],
    );
  }

  /// `HolidayStatus`
  String get pageEntitiesEmployeeHolidayHolidayStatusField {
    return Intl.message(
      'HolidayStatus',
      name: 'pageEntitiesEmployeeHolidayHolidayStatusField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesEmployeeHolidayNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesEmployeeHolidayNoteField',
      desc: '',
      args: [],
    );
  }

  /// `RejectionReason`
  String get pageEntitiesEmployeeHolidayRejectionReasonField {
    return Intl.message(
      'RejectionReason',
      name: 'pageEntitiesEmployeeHolidayRejectionReasonField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesEmployeeHolidayCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesEmployeeHolidayCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesEmployeeHolidayLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesEmployeeHolidayLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesEmployeeHolidayClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesEmployeeHolidayClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEmployeeHolidayHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEmployeeHolidayHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeLocations list`
  String get pageEntitiesEmployeeLocationListTitle {
    return Intl.message(
      'EmployeeLocations list',
      name: 'pageEntitiesEmployeeLocationListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit EmployeeLocations`
  String get pageEntitiesEmployeeLocationUpdateTitle {
    return Intl.message(
      'Edit EmployeeLocations',
      name: 'pageEntitiesEmployeeLocationUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create EmployeeLocations`
  String get pageEntitiesEmployeeLocationCreateTitle {
    return Intl.message(
      'Create EmployeeLocations',
      name: 'pageEntitiesEmployeeLocationCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeLocations View`
  String get pageEntitiesEmployeeLocationViewTitle {
    return Intl.message(
      'EmployeeLocations View',
      name: 'pageEntitiesEmployeeLocationViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete EmployeeLocations`
  String get pageEntitiesEmployeeLocationDeletePopupTitle {
    return Intl.message(
      'Delete EmployeeLocations',
      name: 'pageEntitiesEmployeeLocationDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `EmployeeLocation deleted successfuly`
  String get pageEntitiesEmployeeLocationDeleteOk {
    return Intl.message(
      'EmployeeLocation deleted successfuly',
      name: 'pageEntitiesEmployeeLocationDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Latitude`
  String get pageEntitiesEmployeeLocationLatitudeField {
    return Intl.message(
      'Latitude',
      name: 'pageEntitiesEmployeeLocationLatitudeField',
      desc: '',
      args: [],
    );
  }

  /// `Longitude`
  String get pageEntitiesEmployeeLocationLongitudeField {
    return Intl.message(
      'Longitude',
      name: 'pageEntitiesEmployeeLocationLongitudeField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesEmployeeLocationCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesEmployeeLocationCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesEmployeeLocationLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesEmployeeLocationLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesEmployeeLocationClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesEmployeeLocationClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEmployeeLocationHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEmployeeLocationHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Equalities list`
  String get pageEntitiesEqualityListTitle {
    return Intl.message(
      'Equalities list',
      name: 'pageEntitiesEqualityListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Equalities`
  String get pageEntitiesEqualityUpdateTitle {
    return Intl.message(
      'Edit Equalities',
      name: 'pageEntitiesEqualityUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Equalities`
  String get pageEntitiesEqualityCreateTitle {
    return Intl.message(
      'Create Equalities',
      name: 'pageEntitiesEqualityCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Equalities View`
  String get pageEntitiesEqualityViewTitle {
    return Intl.message(
      'Equalities View',
      name: 'pageEntitiesEqualityViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Equalities`
  String get pageEntitiesEqualityDeletePopupTitle {
    return Intl.message(
      'Delete Equalities',
      name: 'pageEntitiesEqualityDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Equality deleted successfuly`
  String get pageEntitiesEqualityDeleteOk {
    return Intl.message(
      'Equality deleted successfuly',
      name: 'pageEntitiesEqualityDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Gender`
  String get pageEntitiesEqualityGenderField {
    return Intl.message(
      'Gender',
      name: 'pageEntitiesEqualityGenderField',
      desc: '',
      args: [],
    );
  }

  /// `MaritalStatus`
  String get pageEntitiesEqualityMaritalStatusField {
    return Intl.message(
      'MaritalStatus',
      name: 'pageEntitiesEqualityMaritalStatusField',
      desc: '',
      args: [],
    );
  }

  /// `Religion`
  String get pageEntitiesEqualityReligionField {
    return Intl.message(
      'Religion',
      name: 'pageEntitiesEqualityReligionField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesEqualityCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesEqualityCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesEqualityLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesEqualityLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesEqualityClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesEqualityClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesEqualityHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesEqualityHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `ExtraData list`
  String get pageEntitiesExtraDataListTitle {
    return Intl.message(
      'ExtraData list',
      name: 'pageEntitiesExtraDataListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit ExtraData`
  String get pageEntitiesExtraDataUpdateTitle {
    return Intl.message(
      'Edit ExtraData',
      name: 'pageEntitiesExtraDataUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create ExtraData`
  String get pageEntitiesExtraDataCreateTitle {
    return Intl.message(
      'Create ExtraData',
      name: 'pageEntitiesExtraDataCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `ExtraData View`
  String get pageEntitiesExtraDataViewTitle {
    return Intl.message(
      'ExtraData View',
      name: 'pageEntitiesExtraDataViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete ExtraData`
  String get pageEntitiesExtraDataDeletePopupTitle {
    return Intl.message(
      'Delete ExtraData',
      name: 'pageEntitiesExtraDataDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `ExtraData deleted successfuly`
  String get pageEntitiesExtraDataDeleteOk {
    return Intl.message(
      'ExtraData deleted successfuly',
      name: 'pageEntitiesExtraDataDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `EntityName`
  String get pageEntitiesExtraDataEntityNameField {
    return Intl.message(
      'EntityName',
      name: 'pageEntitiesExtraDataEntityNameField',
      desc: '',
      args: [],
    );
  }

  /// `EntityId`
  String get pageEntitiesExtraDataEntityIdField {
    return Intl.message(
      'EntityId',
      name: 'pageEntitiesExtraDataEntityIdField',
      desc: '',
      args: [],
    );
  }

  /// `ExtraDataKey`
  String get pageEntitiesExtraDataExtraDataKeyField {
    return Intl.message(
      'ExtraDataKey',
      name: 'pageEntitiesExtraDataExtraDataKeyField',
      desc: '',
      args: [],
    );
  }

  /// `ExtraDataValue`
  String get pageEntitiesExtraDataExtraDataValueField {
    return Intl.message(
      'ExtraDataValue',
      name: 'pageEntitiesExtraDataExtraDataValueField',
      desc: '',
      args: [],
    );
  }

  /// `ExtraDataValueDataType`
  String get pageEntitiesExtraDataExtraDataValueDataTypeField {
    return Intl.message(
      'ExtraDataValueDataType',
      name: 'pageEntitiesExtraDataExtraDataValueDataTypeField',
      desc: '',
      args: [],
    );
  }

  /// `ExtraDataDescription`
  String get pageEntitiesExtraDataExtraDataDescriptionField {
    return Intl.message(
      'ExtraDataDescription',
      name: 'pageEntitiesExtraDataExtraDataDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `ExtraDataDate`
  String get pageEntitiesExtraDataExtraDataDateField {
    return Intl.message(
      'ExtraDataDate',
      name: 'pageEntitiesExtraDataExtraDataDateField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesExtraDataHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesExtraDataHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Invoices list`
  String get pageEntitiesInvoiceListTitle {
    return Intl.message(
      'Invoices list',
      name: 'pageEntitiesInvoiceListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Invoices`
  String get pageEntitiesInvoiceUpdateTitle {
    return Intl.message(
      'Edit Invoices',
      name: 'pageEntitiesInvoiceUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Invoices`
  String get pageEntitiesInvoiceCreateTitle {
    return Intl.message(
      'Create Invoices',
      name: 'pageEntitiesInvoiceCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Invoices View`
  String get pageEntitiesInvoiceViewTitle {
    return Intl.message(
      'Invoices View',
      name: 'pageEntitiesInvoiceViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Invoices`
  String get pageEntitiesInvoiceDeletePopupTitle {
    return Intl.message(
      'Delete Invoices',
      name: 'pageEntitiesInvoiceDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Invoice deleted successfuly`
  String get pageEntitiesInvoiceDeleteOk {
    return Intl.message(
      'Invoice deleted successfuly',
      name: 'pageEntitiesInvoiceDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `TotalAmount`
  String get pageEntitiesInvoiceTotalAmountField {
    return Intl.message(
      'TotalAmount',
      name: 'pageEntitiesInvoiceTotalAmountField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesInvoiceDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesInvoiceDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `InvoiceNumber`
  String get pageEntitiesInvoiceInvoiceNumberField {
    return Intl.message(
      'InvoiceNumber',
      name: 'pageEntitiesInvoiceInvoiceNumberField',
      desc: '',
      args: [],
    );
  }

  /// `GeneratedDate`
  String get pageEntitiesInvoiceGeneratedDateField {
    return Intl.message(
      'GeneratedDate',
      name: 'pageEntitiesInvoiceGeneratedDateField',
      desc: '',
      args: [],
    );
  }

  /// `DueDate`
  String get pageEntitiesInvoiceDueDateField {
    return Intl.message(
      'DueDate',
      name: 'pageEntitiesInvoiceDueDateField',
      desc: '',
      args: [],
    );
  }

  /// `PaymentDate`
  String get pageEntitiesInvoicePaymentDateField {
    return Intl.message(
      'PaymentDate',
      name: 'pageEntitiesInvoicePaymentDateField',
      desc: '',
      args: [],
    );
  }

  /// `InvoiceStatus`
  String get pageEntitiesInvoiceInvoiceStatusField {
    return Intl.message(
      'InvoiceStatus',
      name: 'pageEntitiesInvoiceInvoiceStatusField',
      desc: '',
      args: [],
    );
  }

  /// `Tax`
  String get pageEntitiesInvoiceTaxField {
    return Intl.message(
      'Tax',
      name: 'pageEntitiesInvoiceTaxField',
      desc: '',
      args: [],
    );
  }

  /// `Attribute1`
  String get pageEntitiesInvoiceAttribute1Field {
    return Intl.message(
      'Attribute1',
      name: 'pageEntitiesInvoiceAttribute1Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute2`
  String get pageEntitiesInvoiceAttribute2Field {
    return Intl.message(
      'Attribute2',
      name: 'pageEntitiesInvoiceAttribute2Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute3`
  String get pageEntitiesInvoiceAttribute3Field {
    return Intl.message(
      'Attribute3',
      name: 'pageEntitiesInvoiceAttribute3Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute4`
  String get pageEntitiesInvoiceAttribute4Field {
    return Intl.message(
      'Attribute4',
      name: 'pageEntitiesInvoiceAttribute4Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute5`
  String get pageEntitiesInvoiceAttribute5Field {
    return Intl.message(
      'Attribute5',
      name: 'pageEntitiesInvoiceAttribute5Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute6`
  String get pageEntitiesInvoiceAttribute6Field {
    return Intl.message(
      'Attribute6',
      name: 'pageEntitiesInvoiceAttribute6Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute7`
  String get pageEntitiesInvoiceAttribute7Field {
    return Intl.message(
      'Attribute7',
      name: 'pageEntitiesInvoiceAttribute7Field',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesInvoiceCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesInvoiceCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesInvoiceLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesInvoiceLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesInvoiceClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesInvoiceClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesInvoiceHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesInvoiceHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `MedicalContacts list`
  String get pageEntitiesMedicalContactListTitle {
    return Intl.message(
      'MedicalContacts list',
      name: 'pageEntitiesMedicalContactListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit MedicalContacts`
  String get pageEntitiesMedicalContactUpdateTitle {
    return Intl.message(
      'Edit MedicalContacts',
      name: 'pageEntitiesMedicalContactUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create MedicalContacts`
  String get pageEntitiesMedicalContactCreateTitle {
    return Intl.message(
      'Create MedicalContacts',
      name: 'pageEntitiesMedicalContactCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `MedicalContacts View`
  String get pageEntitiesMedicalContactViewTitle {
    return Intl.message(
      'MedicalContacts View',
      name: 'pageEntitiesMedicalContactViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete MedicalContacts`
  String get pageEntitiesMedicalContactDeletePopupTitle {
    return Intl.message(
      'Delete MedicalContacts',
      name: 'pageEntitiesMedicalContactDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `MedicalContact deleted successfuly`
  String get pageEntitiesMedicalContactDeleteOk {
    return Intl.message(
      'MedicalContact deleted successfuly',
      name: 'pageEntitiesMedicalContactDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `DoctorName`
  String get pageEntitiesMedicalContactDoctorNameField {
    return Intl.message(
      'DoctorName',
      name: 'pageEntitiesMedicalContactDoctorNameField',
      desc: '',
      args: [],
    );
  }

  /// `DoctorSurgery`
  String get pageEntitiesMedicalContactDoctorSurgeryField {
    return Intl.message(
      'DoctorSurgery',
      name: 'pageEntitiesMedicalContactDoctorSurgeryField',
      desc: '',
      args: [],
    );
  }

  /// `DoctorAddress`
  String get pageEntitiesMedicalContactDoctorAddressField {
    return Intl.message(
      'DoctorAddress',
      name: 'pageEntitiesMedicalContactDoctorAddressField',
      desc: '',
      args: [],
    );
  }

  /// `DoctorPhone`
  String get pageEntitiesMedicalContactDoctorPhoneField {
    return Intl.message(
      'DoctorPhone',
      name: 'pageEntitiesMedicalContactDoctorPhoneField',
      desc: '',
      args: [],
    );
  }

  /// `LastVisitedDoctor`
  String get pageEntitiesMedicalContactLastVisitedDoctorField {
    return Intl.message(
      'LastVisitedDoctor',
      name: 'pageEntitiesMedicalContactLastVisitedDoctorField',
      desc: '',
      args: [],
    );
  }

  /// `DistrictNurseName`
  String get pageEntitiesMedicalContactDistrictNurseNameField {
    return Intl.message(
      'DistrictNurseName',
      name: 'pageEntitiesMedicalContactDistrictNurseNameField',
      desc: '',
      args: [],
    );
  }

  /// `DistrictNursePhone`
  String get pageEntitiesMedicalContactDistrictNursePhoneField {
    return Intl.message(
      'DistrictNursePhone',
      name: 'pageEntitiesMedicalContactDistrictNursePhoneField',
      desc: '',
      args: [],
    );
  }

  /// `CareManagerName`
  String get pageEntitiesMedicalContactCareManagerNameField {
    return Intl.message(
      'CareManagerName',
      name: 'pageEntitiesMedicalContactCareManagerNameField',
      desc: '',
      args: [],
    );
  }

  /// `CareManagerPhone`
  String get pageEntitiesMedicalContactCareManagerPhoneField {
    return Intl.message(
      'CareManagerPhone',
      name: 'pageEntitiesMedicalContactCareManagerPhoneField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesMedicalContactCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesMedicalContactCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesMedicalContactLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesMedicalContactLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesMedicalContactClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesMedicalContactClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesMedicalContactHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesMedicalContactHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Notifications list`
  String get pageEntitiesNotificationsListTitle {
    return Intl.message(
      'Notifications list',
      name: 'pageEntitiesNotificationsListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Notifications`
  String get pageEntitiesNotificationsUpdateTitle {
    return Intl.message(
      'Edit Notifications',
      name: 'pageEntitiesNotificationsUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Notifications`
  String get pageEntitiesNotificationsCreateTitle {
    return Intl.message(
      'Create Notifications',
      name: 'pageEntitiesNotificationsCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Notifications View`
  String get pageEntitiesNotificationsViewTitle {
    return Intl.message(
      'Notifications View',
      name: 'pageEntitiesNotificationsViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Notifications`
  String get pageEntitiesNotificationsDeletePopupTitle {
    return Intl.message(
      'Delete Notifications',
      name: 'pageEntitiesNotificationsDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Notifications deleted successfuly`
  String get pageEntitiesNotificationsDeleteOk {
    return Intl.message(
      'Notifications deleted successfuly',
      name: 'pageEntitiesNotificationsDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Title`
  String get pageEntitiesNotificationsTitleField {
    return Intl.message(
      'Title',
      name: 'pageEntitiesNotificationsTitleField',
      desc: '',
      args: [],
    );
  }

  /// `Body`
  String get pageEntitiesNotificationsBodyField {
    return Intl.message(
      'Body',
      name: 'pageEntitiesNotificationsBodyField',
      desc: '',
      args: [],
    );
  }

  /// `NotificationDate`
  String get pageEntitiesNotificationsNotificationDateField {
    return Intl.message(
      'NotificationDate',
      name: 'pageEntitiesNotificationsNotificationDateField',
      desc: '',
      args: [],
    );
  }

  /// `ImageUrl`
  String get pageEntitiesNotificationsImageUrlField {
    return Intl.message(
      'ImageUrl',
      name: 'pageEntitiesNotificationsImageUrlField',
      desc: '',
      args: [],
    );
  }

  /// `SenderId`
  String get pageEntitiesNotificationsSenderIdField {
    return Intl.message(
      'SenderId',
      name: 'pageEntitiesNotificationsSenderIdField',
      desc: '',
      args: [],
    );
  }

  /// `ReceiverId`
  String get pageEntitiesNotificationsReceiverIdField {
    return Intl.message(
      'ReceiverId',
      name: 'pageEntitiesNotificationsReceiverIdField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesNotificationsCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesNotificationsCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesNotificationsLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesNotificationsLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesNotificationsClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesNotificationsClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesNotificationsHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesNotificationsHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Payrolls list`
  String get pageEntitiesPayrollListTitle {
    return Intl.message(
      'Payrolls list',
      name: 'pageEntitiesPayrollListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Payrolls`
  String get pageEntitiesPayrollUpdateTitle {
    return Intl.message(
      'Edit Payrolls',
      name: 'pageEntitiesPayrollUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Payrolls`
  String get pageEntitiesPayrollCreateTitle {
    return Intl.message(
      'Create Payrolls',
      name: 'pageEntitiesPayrollCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Payrolls View`
  String get pageEntitiesPayrollViewTitle {
    return Intl.message(
      'Payrolls View',
      name: 'pageEntitiesPayrollViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Payrolls`
  String get pageEntitiesPayrollDeletePopupTitle {
    return Intl.message(
      'Delete Payrolls',
      name: 'pageEntitiesPayrollDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Payroll deleted successfuly`
  String get pageEntitiesPayrollDeleteOk {
    return Intl.message(
      'Payroll deleted successfuly',
      name: 'pageEntitiesPayrollDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `PaymentDate`
  String get pageEntitiesPayrollPaymentDateField {
    return Intl.message(
      'PaymentDate',
      name: 'pageEntitiesPayrollPaymentDateField',
      desc: '',
      args: [],
    );
  }

  /// `PayPeriod`
  String get pageEntitiesPayrollPayPeriodField {
    return Intl.message(
      'PayPeriod',
      name: 'pageEntitiesPayrollPayPeriodField',
      desc: '',
      args: [],
    );
  }

  /// `TotalHoursWorked`
  String get pageEntitiesPayrollTotalHoursWorkedField {
    return Intl.message(
      'TotalHoursWorked',
      name: 'pageEntitiesPayrollTotalHoursWorkedField',
      desc: '',
      args: [],
    );
  }

  /// `GrossPay`
  String get pageEntitiesPayrollGrossPayField {
    return Intl.message(
      'GrossPay',
      name: 'pageEntitiesPayrollGrossPayField',
      desc: '',
      args: [],
    );
  }

  /// `NetPay`
  String get pageEntitiesPayrollNetPayField {
    return Intl.message(
      'NetPay',
      name: 'pageEntitiesPayrollNetPayField',
      desc: '',
      args: [],
    );
  }

  /// `TotalTax`
  String get pageEntitiesPayrollTotalTaxField {
    return Intl.message(
      'TotalTax',
      name: 'pageEntitiesPayrollTotalTaxField',
      desc: '',
      args: [],
    );
  }

  /// `PayrollStatus`
  String get pageEntitiesPayrollPayrollStatusField {
    return Intl.message(
      'PayrollStatus',
      name: 'pageEntitiesPayrollPayrollStatusField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesPayrollCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesPayrollCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesPayrollLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesPayrollLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesPayrollClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesPayrollClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesPayrollHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesPayrollHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `PowerOfAttorneys list`
  String get pageEntitiesPowerOfAttorneyListTitle {
    return Intl.message(
      'PowerOfAttorneys list',
      name: 'pageEntitiesPowerOfAttorneyListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit PowerOfAttorneys`
  String get pageEntitiesPowerOfAttorneyUpdateTitle {
    return Intl.message(
      'Edit PowerOfAttorneys',
      name: 'pageEntitiesPowerOfAttorneyUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create PowerOfAttorneys`
  String get pageEntitiesPowerOfAttorneyCreateTitle {
    return Intl.message(
      'Create PowerOfAttorneys',
      name: 'pageEntitiesPowerOfAttorneyCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `PowerOfAttorneys View`
  String get pageEntitiesPowerOfAttorneyViewTitle {
    return Intl.message(
      'PowerOfAttorneys View',
      name: 'pageEntitiesPowerOfAttorneyViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete PowerOfAttorneys`
  String get pageEntitiesPowerOfAttorneyDeletePopupTitle {
    return Intl.message(
      'Delete PowerOfAttorneys',
      name: 'pageEntitiesPowerOfAttorneyDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `PowerOfAttorney deleted successfuly`
  String get pageEntitiesPowerOfAttorneyDeleteOk {
    return Intl.message(
      'PowerOfAttorney deleted successfuly',
      name: 'pageEntitiesPowerOfAttorneyDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `PowerOfAttorneyConsent`
  String get pageEntitiesPowerOfAttorneyPowerOfAttorneyConsentField {
    return Intl.message(
      'PowerOfAttorneyConsent',
      name: 'pageEntitiesPowerOfAttorneyPowerOfAttorneyConsentField',
      desc: '',
      args: [],
    );
  }

  /// `HealthAndWelfare`
  String get pageEntitiesPowerOfAttorneyHealthAndWelfareField {
    return Intl.message(
      'HealthAndWelfare',
      name: 'pageEntitiesPowerOfAttorneyHealthAndWelfareField',
      desc: '',
      args: [],
    );
  }

  /// `HealthAndWelfareName`
  String get pageEntitiesPowerOfAttorneyHealthAndWelfareNameField {
    return Intl.message(
      'HealthAndWelfareName',
      name: 'pageEntitiesPowerOfAttorneyHealthAndWelfareNameField',
      desc: '',
      args: [],
    );
  }

  /// `PropertyAndFinAffairs`
  String get pageEntitiesPowerOfAttorneyPropertyAndFinAffairsField {
    return Intl.message(
      'PropertyAndFinAffairs',
      name: 'pageEntitiesPowerOfAttorneyPropertyAndFinAffairsField',
      desc: '',
      args: [],
    );
  }

  /// `PropertyAndFinAffairsName`
  String get pageEntitiesPowerOfAttorneyPropertyAndFinAffairsNameField {
    return Intl.message(
      'PropertyAndFinAffairsName',
      name: 'pageEntitiesPowerOfAttorneyPropertyAndFinAffairsNameField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesPowerOfAttorneyCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesPowerOfAttorneyCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesPowerOfAttorneyLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesPowerOfAttorneyLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesPowerOfAttorneyClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesPowerOfAttorneyClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesPowerOfAttorneyHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesPowerOfAttorneyHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Questions list`
  String get pageEntitiesQuestionListTitle {
    return Intl.message(
      'Questions list',
      name: 'pageEntitiesQuestionListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Questions`
  String get pageEntitiesQuestionUpdateTitle {
    return Intl.message(
      'Edit Questions',
      name: 'pageEntitiesQuestionUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Questions`
  String get pageEntitiesQuestionCreateTitle {
    return Intl.message(
      'Create Questions',
      name: 'pageEntitiesQuestionCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Questions View`
  String get pageEntitiesQuestionViewTitle {
    return Intl.message(
      'Questions View',
      name: 'pageEntitiesQuestionViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Questions`
  String get pageEntitiesQuestionDeletePopupTitle {
    return Intl.message(
      'Delete Questions',
      name: 'pageEntitiesQuestionDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Question deleted successfuly`
  String get pageEntitiesQuestionDeleteOk {
    return Intl.message(
      'Question deleted successfuly',
      name: 'pageEntitiesQuestionDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Question`
  String get pageEntitiesQuestionQuestionField {
    return Intl.message(
      'Question',
      name: 'pageEntitiesQuestionQuestionField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesQuestionDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesQuestionDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `Attribute1`
  String get pageEntitiesQuestionAttribute1Field {
    return Intl.message(
      'Attribute1',
      name: 'pageEntitiesQuestionAttribute1Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute2`
  String get pageEntitiesQuestionAttribute2Field {
    return Intl.message(
      'Attribute2',
      name: 'pageEntitiesQuestionAttribute2Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute3`
  String get pageEntitiesQuestionAttribute3Field {
    return Intl.message(
      'Attribute3',
      name: 'pageEntitiesQuestionAttribute3Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute4`
  String get pageEntitiesQuestionAttribute4Field {
    return Intl.message(
      'Attribute4',
      name: 'pageEntitiesQuestionAttribute4Field',
      desc: '',
      args: [],
    );
  }

  /// `Attribute5`
  String get pageEntitiesQuestionAttribute5Field {
    return Intl.message(
      'Attribute5',
      name: 'pageEntitiesQuestionAttribute5Field',
      desc: '',
      args: [],
    );
  }

  /// `Optional`
  String get pageEntitiesQuestionOptionalField {
    return Intl.message(
      'Optional',
      name: 'pageEntitiesQuestionOptionalField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesQuestionCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesQuestionCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesQuestionLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesQuestionLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesQuestionClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesQuestionClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesQuestionHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesQuestionHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `QuestionTypes list`
  String get pageEntitiesQuestionTypeListTitle {
    return Intl.message(
      'QuestionTypes list',
      name: 'pageEntitiesQuestionTypeListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit QuestionTypes`
  String get pageEntitiesQuestionTypeUpdateTitle {
    return Intl.message(
      'Edit QuestionTypes',
      name: 'pageEntitiesQuestionTypeUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create QuestionTypes`
  String get pageEntitiesQuestionTypeCreateTitle {
    return Intl.message(
      'Create QuestionTypes',
      name: 'pageEntitiesQuestionTypeCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `QuestionTypes View`
  String get pageEntitiesQuestionTypeViewTitle {
    return Intl.message(
      'QuestionTypes View',
      name: 'pageEntitiesQuestionTypeViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete QuestionTypes`
  String get pageEntitiesQuestionTypeDeletePopupTitle {
    return Intl.message(
      'Delete QuestionTypes',
      name: 'pageEntitiesQuestionTypeDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `QuestionType deleted successfuly`
  String get pageEntitiesQuestionTypeDeleteOk {
    return Intl.message(
      'QuestionType deleted successfuly',
      name: 'pageEntitiesQuestionTypeDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `QuestionType`
  String get pageEntitiesQuestionTypeQuestionTypeField {
    return Intl.message(
      'QuestionType',
      name: 'pageEntitiesQuestionTypeQuestionTypeField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesQuestionTypeLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesQuestionTypeLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `RelationshipTypes list`
  String get pageEntitiesRelationshipTypeListTitle {
    return Intl.message(
      'RelationshipTypes list',
      name: 'pageEntitiesRelationshipTypeListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit RelationshipTypes`
  String get pageEntitiesRelationshipTypeUpdateTitle {
    return Intl.message(
      'Edit RelationshipTypes',
      name: 'pageEntitiesRelationshipTypeUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create RelationshipTypes`
  String get pageEntitiesRelationshipTypeCreateTitle {
    return Intl.message(
      'Create RelationshipTypes',
      name: 'pageEntitiesRelationshipTypeCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `RelationshipTypes View`
  String get pageEntitiesRelationshipTypeViewTitle {
    return Intl.message(
      'RelationshipTypes View',
      name: 'pageEntitiesRelationshipTypeViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete RelationshipTypes`
  String get pageEntitiesRelationshipTypeDeletePopupTitle {
    return Intl.message(
      'Delete RelationshipTypes',
      name: 'pageEntitiesRelationshipTypeDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `RelationshipType deleted successfuly`
  String get pageEntitiesRelationshipTypeDeleteOk {
    return Intl.message(
      'RelationshipType deleted successfuly',
      name: 'pageEntitiesRelationshipTypeDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `RelationType`
  String get pageEntitiesRelationshipTypeRelationTypeField {
    return Intl.message(
      'RelationType',
      name: 'pageEntitiesRelationshipTypeRelationTypeField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesRelationshipTypeDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesRelationshipTypeDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesRelationshipTypeClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesRelationshipTypeClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesRelationshipTypeHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesRelationshipTypeHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `ServceUserDocuments list`
  String get pageEntitiesServceUserDocumentListTitle {
    return Intl.message(
      'ServceUserDocuments list',
      name: 'pageEntitiesServceUserDocumentListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit ServceUserDocuments`
  String get pageEntitiesServceUserDocumentUpdateTitle {
    return Intl.message(
      'Edit ServceUserDocuments',
      name: 'pageEntitiesServceUserDocumentUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create ServceUserDocuments`
  String get pageEntitiesServceUserDocumentCreateTitle {
    return Intl.message(
      'Create ServceUserDocuments',
      name: 'pageEntitiesServceUserDocumentCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServceUserDocuments View`
  String get pageEntitiesServceUserDocumentViewTitle {
    return Intl.message(
      'ServceUserDocuments View',
      name: 'pageEntitiesServceUserDocumentViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete ServceUserDocuments`
  String get pageEntitiesServceUserDocumentDeletePopupTitle {
    return Intl.message(
      'Delete ServceUserDocuments',
      name: 'pageEntitiesServceUserDocumentDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServceUserDocument deleted successfuly`
  String get pageEntitiesServceUserDocumentDeleteOk {
    return Intl.message(
      'ServceUserDocument deleted successfuly',
      name: 'pageEntitiesServceUserDocumentDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `DocumentName`
  String get pageEntitiesServceUserDocumentDocumentNameField {
    return Intl.message(
      'DocumentName',
      name: 'pageEntitiesServceUserDocumentDocumentNameField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentNumber`
  String get pageEntitiesServceUserDocumentDocumentNumberField {
    return Intl.message(
      'DocumentNumber',
      name: 'pageEntitiesServceUserDocumentDocumentNumberField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentStatus`
  String get pageEntitiesServceUserDocumentDocumentStatusField {
    return Intl.message(
      'DocumentStatus',
      name: 'pageEntitiesServceUserDocumentDocumentStatusField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesServceUserDocumentNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesServceUserDocumentNoteField',
      desc: '',
      args: [],
    );
  }

  /// `IssuedDate`
  String get pageEntitiesServceUserDocumentIssuedDateField {
    return Intl.message(
      'IssuedDate',
      name: 'pageEntitiesServceUserDocumentIssuedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ExpiryDate`
  String get pageEntitiesServceUserDocumentExpiryDateField {
    return Intl.message(
      'ExpiryDate',
      name: 'pageEntitiesServceUserDocumentExpiryDateField',
      desc: '',
      args: [],
    );
  }

  /// `UploadedDate`
  String get pageEntitiesServceUserDocumentUploadedDateField {
    return Intl.message(
      'UploadedDate',
      name: 'pageEntitiesServceUserDocumentUploadedDateField',
      desc: '',
      args: [],
    );
  }

  /// `DocumentFileUrl`
  String get pageEntitiesServceUserDocumentDocumentFileUrlField {
    return Intl.message(
      'DocumentFileUrl',
      name: 'pageEntitiesServceUserDocumentDocumentFileUrlField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesServceUserDocumentCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesServceUserDocumentCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesServceUserDocumentLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesServceUserDocumentLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesServceUserDocumentClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesServceUserDocumentClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesServceUserDocumentHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesServceUserDocumentHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceOrders list`
  String get pageEntitiesServiceOrderListTitle {
    return Intl.message(
      'ServiceOrders list',
      name: 'pageEntitiesServiceOrderListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit ServiceOrders`
  String get pageEntitiesServiceOrderUpdateTitle {
    return Intl.message(
      'Edit ServiceOrders',
      name: 'pageEntitiesServiceOrderUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create ServiceOrders`
  String get pageEntitiesServiceOrderCreateTitle {
    return Intl.message(
      'Create ServiceOrders',
      name: 'pageEntitiesServiceOrderCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceOrders View`
  String get pageEntitiesServiceOrderViewTitle {
    return Intl.message(
      'ServiceOrders View',
      name: 'pageEntitiesServiceOrderViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete ServiceOrders`
  String get pageEntitiesServiceOrderDeletePopupTitle {
    return Intl.message(
      'Delete ServiceOrders',
      name: 'pageEntitiesServiceOrderDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceOrder deleted successfuly`
  String get pageEntitiesServiceOrderDeleteOk {
    return Intl.message(
      'ServiceOrder deleted successfuly',
      name: 'pageEntitiesServiceOrderDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Title`
  String get pageEntitiesServiceOrderTitleField {
    return Intl.message(
      'Title',
      name: 'pageEntitiesServiceOrderTitleField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceDescription`
  String get pageEntitiesServiceOrderServiceDescriptionField {
    return Intl.message(
      'ServiceDescription',
      name: 'pageEntitiesServiceOrderServiceDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceHourlyRate`
  String get pageEntitiesServiceOrderServiceHourlyRateField {
    return Intl.message(
      'ServiceHourlyRate',
      name: 'pageEntitiesServiceOrderServiceHourlyRateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesServiceOrderClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesServiceOrderClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesServiceOrderCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesServiceOrderCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesServiceOrderLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesServiceOrderLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesServiceOrderHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesServiceOrderHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUsers list`
  String get pageEntitiesServiceUserListTitle {
    return Intl.message(
      'ServiceUsers list',
      name: 'pageEntitiesServiceUserListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit ServiceUsers`
  String get pageEntitiesServiceUserUpdateTitle {
    return Intl.message(
      'Edit ServiceUsers',
      name: 'pageEntitiesServiceUserUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create ServiceUsers`
  String get pageEntitiesServiceUserCreateTitle {
    return Intl.message(
      'Create ServiceUsers',
      name: 'pageEntitiesServiceUserCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUsers View`
  String get pageEntitiesServiceUserViewTitle {
    return Intl.message(
      'ServiceUsers View',
      name: 'pageEntitiesServiceUserViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete ServiceUsers`
  String get pageEntitiesServiceUserDeletePopupTitle {
    return Intl.message(
      'Delete ServiceUsers',
      name: 'pageEntitiesServiceUserDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUser deleted successfuly`
  String get pageEntitiesServiceUserDeleteOk {
    return Intl.message(
      'ServiceUser deleted successfuly',
      name: 'pageEntitiesServiceUserDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Titlle`
  String get pageEntitiesServiceUserTitlleField {
    return Intl.message(
      'Titlle',
      name: 'pageEntitiesServiceUserTitlleField',
      desc: '',
      args: [],
    );
  }

  /// `FirstName`
  String get pageEntitiesServiceUserFirstNameField {
    return Intl.message(
      'FirstName',
      name: 'pageEntitiesServiceUserFirstNameField',
      desc: '',
      args: [],
    );
  }

  /// `MiddleName`
  String get pageEntitiesServiceUserMiddleNameField {
    return Intl.message(
      'MiddleName',
      name: 'pageEntitiesServiceUserMiddleNameField',
      desc: '',
      args: [],
    );
  }

  /// `LastName`
  String get pageEntitiesServiceUserLastNameField {
    return Intl.message(
      'LastName',
      name: 'pageEntitiesServiceUserLastNameField',
      desc: '',
      args: [],
    );
  }

  /// `PreferredName`
  String get pageEntitiesServiceUserPreferredNameField {
    return Intl.message(
      'PreferredName',
      name: 'pageEntitiesServiceUserPreferredNameField',
      desc: '',
      args: [],
    );
  }

  /// `Email`
  String get pageEntitiesServiceUserEmailField {
    return Intl.message(
      'Email',
      name: 'pageEntitiesServiceUserEmailField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserCode`
  String get pageEntitiesServiceUserServiceUserCodeField {
    return Intl.message(
      'ServiceUserCode',
      name: 'pageEntitiesServiceUserServiceUserCodeField',
      desc: '',
      args: [],
    );
  }

  /// `DateOfBirth`
  String get pageEntitiesServiceUserDateOfBirthField {
    return Intl.message(
      'DateOfBirth',
      name: 'pageEntitiesServiceUserDateOfBirthField',
      desc: '',
      args: [],
    );
  }

  /// `LastVisitDate`
  String get pageEntitiesServiceUserLastVisitDateField {
    return Intl.message(
      'LastVisitDate',
      name: 'pageEntitiesServiceUserLastVisitDateField',
      desc: '',
      args: [],
    );
  }

  /// `StartDate`
  String get pageEntitiesServiceUserStartDateField {
    return Intl.message(
      'StartDate',
      name: 'pageEntitiesServiceUserStartDateField',
      desc: '',
      args: [],
    );
  }

  /// `SupportType`
  String get pageEntitiesServiceUserSupportTypeField {
    return Intl.message(
      'SupportType',
      name: 'pageEntitiesServiceUserSupportTypeField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserCategory`
  String get pageEntitiesServiceUserServiceUserCategoryField {
    return Intl.message(
      'ServiceUserCategory',
      name: 'pageEntitiesServiceUserServiceUserCategoryField',
      desc: '',
      args: [],
    );
  }

  /// `Vulnerability`
  String get pageEntitiesServiceUserVulnerabilityField {
    return Intl.message(
      'Vulnerability',
      name: 'pageEntitiesServiceUserVulnerabilityField',
      desc: '',
      args: [],
    );
  }

  /// `ServicePriority`
  String get pageEntitiesServiceUserServicePriorityField {
    return Intl.message(
      'ServicePriority',
      name: 'pageEntitiesServiceUserServicePriorityField',
      desc: '',
      args: [],
    );
  }

  /// `Source`
  String get pageEntitiesServiceUserSourceField {
    return Intl.message(
      'Source',
      name: 'pageEntitiesServiceUserSourceField',
      desc: '',
      args: [],
    );
  }

  /// `Status`
  String get pageEntitiesServiceUserStatusField {
    return Intl.message(
      'Status',
      name: 'pageEntitiesServiceUserStatusField',
      desc: '',
      args: [],
    );
  }

  /// `FirstLanguage`
  String get pageEntitiesServiceUserFirstLanguageField {
    return Intl.message(
      'FirstLanguage',
      name: 'pageEntitiesServiceUserFirstLanguageField',
      desc: '',
      args: [],
    );
  }

  /// `InterpreterRequired`
  String get pageEntitiesServiceUserInterpreterRequiredField {
    return Intl.message(
      'InterpreterRequired',
      name: 'pageEntitiesServiceUserInterpreterRequiredField',
      desc: '',
      args: [],
    );
  }

  /// `ActivatedDate`
  String get pageEntitiesServiceUserActivatedDateField {
    return Intl.message(
      'ActivatedDate',
      name: 'pageEntitiesServiceUserActivatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ProfilePhotoUrl`
  String get pageEntitiesServiceUserProfilePhotoUrlField {
    return Intl.message(
      'ProfilePhotoUrl',
      name: 'pageEntitiesServiceUserProfilePhotoUrlField',
      desc: '',
      args: [],
    );
  }

  /// `LastRecordedHeight`
  String get pageEntitiesServiceUserLastRecordedHeightField {
    return Intl.message(
      'LastRecordedHeight',
      name: 'pageEntitiesServiceUserLastRecordedHeightField',
      desc: '',
      args: [],
    );
  }

  /// `LastRecordedWeight`
  String get pageEntitiesServiceUserLastRecordedWeightField {
    return Intl.message(
      'LastRecordedWeight',
      name: 'pageEntitiesServiceUserLastRecordedWeightField',
      desc: '',
      args: [],
    );
  }

  /// `HasMedicalCondition`
  String get pageEntitiesServiceUserHasMedicalConditionField {
    return Intl.message(
      'HasMedicalCondition',
      name: 'pageEntitiesServiceUserHasMedicalConditionField',
      desc: '',
      args: [],
    );
  }

  /// `MedicalConditionSummary`
  String get pageEntitiesServiceUserMedicalConditionSummaryField {
    return Intl.message(
      'MedicalConditionSummary',
      name: 'pageEntitiesServiceUserMedicalConditionSummaryField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesServiceUserCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesServiceUserCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesServiceUserLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesServiceUserLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesServiceUserClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesServiceUserClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesServiceUserHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesServiceUserHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserContacts list`
  String get pageEntitiesServiceUserContactListTitle {
    return Intl.message(
      'ServiceUserContacts list',
      name: 'pageEntitiesServiceUserContactListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit ServiceUserContacts`
  String get pageEntitiesServiceUserContactUpdateTitle {
    return Intl.message(
      'Edit ServiceUserContacts',
      name: 'pageEntitiesServiceUserContactUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create ServiceUserContacts`
  String get pageEntitiesServiceUserContactCreateTitle {
    return Intl.message(
      'Create ServiceUserContacts',
      name: 'pageEntitiesServiceUserContactCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserContacts View`
  String get pageEntitiesServiceUserContactViewTitle {
    return Intl.message(
      'ServiceUserContacts View',
      name: 'pageEntitiesServiceUserContactViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete ServiceUserContacts`
  String get pageEntitiesServiceUserContactDeletePopupTitle {
    return Intl.message(
      'Delete ServiceUserContacts',
      name: 'pageEntitiesServiceUserContactDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserContact deleted successfuly`
  String get pageEntitiesServiceUserContactDeleteOk {
    return Intl.message(
      'ServiceUserContact deleted successfuly',
      name: 'pageEntitiesServiceUserContactDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Address`
  String get pageEntitiesServiceUserContactAddressField {
    return Intl.message(
      'Address',
      name: 'pageEntitiesServiceUserContactAddressField',
      desc: '',
      args: [],
    );
  }

  /// `CityOrTown`
  String get pageEntitiesServiceUserContactCityOrTownField {
    return Intl.message(
      'CityOrTown',
      name: 'pageEntitiesServiceUserContactCityOrTownField',
      desc: '',
      args: [],
    );
  }

  /// `County`
  String get pageEntitiesServiceUserContactCountyField {
    return Intl.message(
      'County',
      name: 'pageEntitiesServiceUserContactCountyField',
      desc: '',
      args: [],
    );
  }

  /// `PostCode`
  String get pageEntitiesServiceUserContactPostCodeField {
    return Intl.message(
      'PostCode',
      name: 'pageEntitiesServiceUserContactPostCodeField',
      desc: '',
      args: [],
    );
  }

  /// `Telephone`
  String get pageEntitiesServiceUserContactTelephoneField {
    return Intl.message(
      'Telephone',
      name: 'pageEntitiesServiceUserContactTelephoneField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesServiceUserContactCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesServiceUserContactCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesServiceUserContactLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesServiceUserContactLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesServiceUserContactClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesServiceUserContactClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesServiceUserContactHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesServiceUserContactHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserEvents list`
  String get pageEntitiesServiceUserEventListTitle {
    return Intl.message(
      'ServiceUserEvents list',
      name: 'pageEntitiesServiceUserEventListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit ServiceUserEvents`
  String get pageEntitiesServiceUserEventUpdateTitle {
    return Intl.message(
      'Edit ServiceUserEvents',
      name: 'pageEntitiesServiceUserEventUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create ServiceUserEvents`
  String get pageEntitiesServiceUserEventCreateTitle {
    return Intl.message(
      'Create ServiceUserEvents',
      name: 'pageEntitiesServiceUserEventCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserEvents View`
  String get pageEntitiesServiceUserEventViewTitle {
    return Intl.message(
      'ServiceUserEvents View',
      name: 'pageEntitiesServiceUserEventViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete ServiceUserEvents`
  String get pageEntitiesServiceUserEventDeletePopupTitle {
    return Intl.message(
      'Delete ServiceUserEvents',
      name: 'pageEntitiesServiceUserEventDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserEvent deleted successfuly`
  String get pageEntitiesServiceUserEventDeleteOk {
    return Intl.message(
      'ServiceUserEvent deleted successfuly',
      name: 'pageEntitiesServiceUserEventDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `EventTitle`
  String get pageEntitiesServiceUserEventEventTitleField {
    return Intl.message(
      'EventTitle',
      name: 'pageEntitiesServiceUserEventEventTitleField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesServiceUserEventDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesServiceUserEventDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserEventStatus`
  String get pageEntitiesServiceUserEventServiceUserEventStatusField {
    return Intl.message(
      'ServiceUserEventStatus',
      name: 'pageEntitiesServiceUserEventServiceUserEventStatusField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserEventType`
  String get pageEntitiesServiceUserEventServiceUserEventTypeField {
    return Intl.message(
      'ServiceUserEventType',
      name: 'pageEntitiesServiceUserEventServiceUserEventTypeField',
      desc: '',
      args: [],
    );
  }

  /// `Priority`
  String get pageEntitiesServiceUserEventPriorityField {
    return Intl.message(
      'Priority',
      name: 'pageEntitiesServiceUserEventPriorityField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesServiceUserEventNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesServiceUserEventNoteField',
      desc: '',
      args: [],
    );
  }

  /// `DateOfEvent`
  String get pageEntitiesServiceUserEventDateOfEventField {
    return Intl.message(
      'DateOfEvent',
      name: 'pageEntitiesServiceUserEventDateOfEventField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesServiceUserEventCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesServiceUserEventCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesServiceUserEventLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesServiceUserEventLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesServiceUserEventClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesServiceUserEventClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesServiceUserEventHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesServiceUserEventHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserLocations list`
  String get pageEntitiesServiceUserLocationListTitle {
    return Intl.message(
      'ServiceUserLocations list',
      name: 'pageEntitiesServiceUserLocationListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit ServiceUserLocations`
  String get pageEntitiesServiceUserLocationUpdateTitle {
    return Intl.message(
      'Edit ServiceUserLocations',
      name: 'pageEntitiesServiceUserLocationUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create ServiceUserLocations`
  String get pageEntitiesServiceUserLocationCreateTitle {
    return Intl.message(
      'Create ServiceUserLocations',
      name: 'pageEntitiesServiceUserLocationCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserLocations View`
  String get pageEntitiesServiceUserLocationViewTitle {
    return Intl.message(
      'ServiceUserLocations View',
      name: 'pageEntitiesServiceUserLocationViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete ServiceUserLocations`
  String get pageEntitiesServiceUserLocationDeletePopupTitle {
    return Intl.message(
      'Delete ServiceUserLocations',
      name: 'pageEntitiesServiceUserLocationDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `ServiceUserLocation deleted successfuly`
  String get pageEntitiesServiceUserLocationDeleteOk {
    return Intl.message(
      'ServiceUserLocation deleted successfuly',
      name: 'pageEntitiesServiceUserLocationDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Latitude`
  String get pageEntitiesServiceUserLocationLatitudeField {
    return Intl.message(
      'Latitude',
      name: 'pageEntitiesServiceUserLocationLatitudeField',
      desc: '',
      args: [],
    );
  }

  /// `Longitude`
  String get pageEntitiesServiceUserLocationLongitudeField {
    return Intl.message(
      'Longitude',
      name: 'pageEntitiesServiceUserLocationLongitudeField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesServiceUserLocationCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesServiceUserLocationCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesServiceUserLocationLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesServiceUserLocationLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesServiceUserLocationClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesServiceUserLocationClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesServiceUserLocationHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesServiceUserLocationHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `SystemEventsHistories list`
  String get pageEntitiesSystemEventsHistoryListTitle {
    return Intl.message(
      'SystemEventsHistories list',
      name: 'pageEntitiesSystemEventsHistoryListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit SystemEventsHistories`
  String get pageEntitiesSystemEventsHistoryUpdateTitle {
    return Intl.message(
      'Edit SystemEventsHistories',
      name: 'pageEntitiesSystemEventsHistoryUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create SystemEventsHistories`
  String get pageEntitiesSystemEventsHistoryCreateTitle {
    return Intl.message(
      'Create SystemEventsHistories',
      name: 'pageEntitiesSystemEventsHistoryCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `SystemEventsHistories View`
  String get pageEntitiesSystemEventsHistoryViewTitle {
    return Intl.message(
      'SystemEventsHistories View',
      name: 'pageEntitiesSystemEventsHistoryViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete SystemEventsHistories`
  String get pageEntitiesSystemEventsHistoryDeletePopupTitle {
    return Intl.message(
      'Delete SystemEventsHistories',
      name: 'pageEntitiesSystemEventsHistoryDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `SystemEventsHistory deleted successfuly`
  String get pageEntitiesSystemEventsHistoryDeleteOk {
    return Intl.message(
      'SystemEventsHistory deleted successfuly',
      name: 'pageEntitiesSystemEventsHistoryDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `EventName`
  String get pageEntitiesSystemEventsHistoryEventNameField {
    return Intl.message(
      'EventName',
      name: 'pageEntitiesSystemEventsHistoryEventNameField',
      desc: '',
      args: [],
    );
  }

  /// `EventDate`
  String get pageEntitiesSystemEventsHistoryEventDateField {
    return Intl.message(
      'EventDate',
      name: 'pageEntitiesSystemEventsHistoryEventDateField',
      desc: '',
      args: [],
    );
  }

  /// `EventApi`
  String get pageEntitiesSystemEventsHistoryEventApiField {
    return Intl.message(
      'EventApi',
      name: 'pageEntitiesSystemEventsHistoryEventApiField',
      desc: '',
      args: [],
    );
  }

  /// `IpAddress`
  String get pageEntitiesSystemEventsHistoryIpAddressField {
    return Intl.message(
      'IpAddress',
      name: 'pageEntitiesSystemEventsHistoryIpAddressField',
      desc: '',
      args: [],
    );
  }

  /// `EventNote`
  String get pageEntitiesSystemEventsHistoryEventNoteField {
    return Intl.message(
      'EventNote',
      name: 'pageEntitiesSystemEventsHistoryEventNoteField',
      desc: '',
      args: [],
    );
  }

  /// `EventEntityName`
  String get pageEntitiesSystemEventsHistoryEventEntityNameField {
    return Intl.message(
      'EventEntityName',
      name: 'pageEntitiesSystemEventsHistoryEventEntityNameField',
      desc: '',
      args: [],
    );
  }

  /// `EventEntityId`
  String get pageEntitiesSystemEventsHistoryEventEntityIdField {
    return Intl.message(
      'EventEntityId',
      name: 'pageEntitiesSystemEventsHistoryEventEntityIdField',
      desc: '',
      args: [],
    );
  }

  /// `IsSuspecious`
  String get pageEntitiesSystemEventsHistoryIsSuspeciousField {
    return Intl.message(
      'IsSuspecious',
      name: 'pageEntitiesSystemEventsHistoryIsSuspeciousField',
      desc: '',
      args: [],
    );
  }

  /// `CallerEmail`
  String get pageEntitiesSystemEventsHistoryCallerEmailField {
    return Intl.message(
      'CallerEmail',
      name: 'pageEntitiesSystemEventsHistoryCallerEmailField',
      desc: '',
      args: [],
    );
  }

  /// `CallerId`
  String get pageEntitiesSystemEventsHistoryCallerIdField {
    return Intl.message(
      'CallerId',
      name: 'pageEntitiesSystemEventsHistoryCallerIdField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesSystemEventsHistoryClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesSystemEventsHistoryClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `SystemSettings list`
  String get pageEntitiesSystemSettingListTitle {
    return Intl.message(
      'SystemSettings list',
      name: 'pageEntitiesSystemSettingListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit SystemSettings`
  String get pageEntitiesSystemSettingUpdateTitle {
    return Intl.message(
      'Edit SystemSettings',
      name: 'pageEntitiesSystemSettingUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create SystemSettings`
  String get pageEntitiesSystemSettingCreateTitle {
    return Intl.message(
      'Create SystemSettings',
      name: 'pageEntitiesSystemSettingCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `SystemSettings View`
  String get pageEntitiesSystemSettingViewTitle {
    return Intl.message(
      'SystemSettings View',
      name: 'pageEntitiesSystemSettingViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete SystemSettings`
  String get pageEntitiesSystemSettingDeletePopupTitle {
    return Intl.message(
      'Delete SystemSettings',
      name: 'pageEntitiesSystemSettingDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `SystemSetting deleted successfuly`
  String get pageEntitiesSystemSettingDeleteOk {
    return Intl.message(
      'SystemSetting deleted successfuly',
      name: 'pageEntitiesSystemSettingDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `FieldName`
  String get pageEntitiesSystemSettingFieldNameField {
    return Intl.message(
      'FieldName',
      name: 'pageEntitiesSystemSettingFieldNameField',
      desc: '',
      args: [],
    );
  }

  /// `FieldValue`
  String get pageEntitiesSystemSettingFieldValueField {
    return Intl.message(
      'FieldValue',
      name: 'pageEntitiesSystemSettingFieldValueField',
      desc: '',
      args: [],
    );
  }

  /// `DefaultValue`
  String get pageEntitiesSystemSettingDefaultValueField {
    return Intl.message(
      'DefaultValue',
      name: 'pageEntitiesSystemSettingDefaultValueField',
      desc: '',
      args: [],
    );
  }

  /// `SettingEnabled`
  String get pageEntitiesSystemSettingSettingEnabledField {
    return Intl.message(
      'SettingEnabled',
      name: 'pageEntitiesSystemSettingSettingEnabledField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesSystemSettingCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesSystemSettingCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `UpdatedDate`
  String get pageEntitiesSystemSettingUpdatedDateField {
    return Intl.message(
      'UpdatedDate',
      name: 'pageEntitiesSystemSettingUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesSystemSettingClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesSystemSettingClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesSystemSettingHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesSystemSettingHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Tasks list`
  String get pageEntitiesTaskListTitle {
    return Intl.message(
      'Tasks list',
      name: 'pageEntitiesTaskListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Tasks`
  String get pageEntitiesTaskUpdateTitle {
    return Intl.message(
      'Edit Tasks',
      name: 'pageEntitiesTaskUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Tasks`
  String get pageEntitiesTaskCreateTitle {
    return Intl.message(
      'Create Tasks',
      name: 'pageEntitiesTaskCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Tasks View`
  String get pageEntitiesTaskViewTitle {
    return Intl.message(
      'Tasks View',
      name: 'pageEntitiesTaskViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Tasks`
  String get pageEntitiesTaskDeletePopupTitle {
    return Intl.message(
      'Delete Tasks',
      name: 'pageEntitiesTaskDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Task deleted successfuly`
  String get pageEntitiesTaskDeleteOk {
    return Intl.message(
      'Task deleted successfuly',
      name: 'pageEntitiesTaskDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `TaskName`
  String get pageEntitiesTaskTaskNameField {
    return Intl.message(
      'TaskName',
      name: 'pageEntitiesTaskTaskNameField',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesTaskDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesTaskDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `DateOfTask`
  String get pageEntitiesTaskDateOfTaskField {
    return Intl.message(
      'DateOfTask',
      name: 'pageEntitiesTaskDateOfTaskField',
      desc: '',
      args: [],
    );
  }

  /// `StartTime`
  String get pageEntitiesTaskStartTimeField {
    return Intl.message(
      'StartTime',
      name: 'pageEntitiesTaskStartTimeField',
      desc: '',
      args: [],
    );
  }

  /// `EndTime`
  String get pageEntitiesTaskEndTimeField {
    return Intl.message(
      'EndTime',
      name: 'pageEntitiesTaskEndTimeField',
      desc: '',
      args: [],
    );
  }

  /// `Status`
  String get pageEntitiesTaskStatusField {
    return Intl.message(
      'Status',
      name: 'pageEntitiesTaskStatusField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesTaskCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesTaskCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesTaskLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesTaskLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesTaskClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesTaskClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesTaskHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesTaskHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `TerminalDevices list`
  String get pageEntitiesTerminalDeviceListTitle {
    return Intl.message(
      'TerminalDevices list',
      name: 'pageEntitiesTerminalDeviceListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit TerminalDevices`
  String get pageEntitiesTerminalDeviceUpdateTitle {
    return Intl.message(
      'Edit TerminalDevices',
      name: 'pageEntitiesTerminalDeviceUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create TerminalDevices`
  String get pageEntitiesTerminalDeviceCreateTitle {
    return Intl.message(
      'Create TerminalDevices',
      name: 'pageEntitiesTerminalDeviceCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `TerminalDevices View`
  String get pageEntitiesTerminalDeviceViewTitle {
    return Intl.message(
      'TerminalDevices View',
      name: 'pageEntitiesTerminalDeviceViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete TerminalDevices`
  String get pageEntitiesTerminalDeviceDeletePopupTitle {
    return Intl.message(
      'Delete TerminalDevices',
      name: 'pageEntitiesTerminalDeviceDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `TerminalDevice deleted successfuly`
  String get pageEntitiesTerminalDeviceDeleteOk {
    return Intl.message(
      'TerminalDevice deleted successfuly',
      name: 'pageEntitiesTerminalDeviceDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `DeviceName`
  String get pageEntitiesTerminalDeviceDeviceNameField {
    return Intl.message(
      'DeviceName',
      name: 'pageEntitiesTerminalDeviceDeviceNameField',
      desc: '',
      args: [],
    );
  }

  /// `DeviceModel`
  String get pageEntitiesTerminalDeviceDeviceModelField {
    return Intl.message(
      'DeviceModel',
      name: 'pageEntitiesTerminalDeviceDeviceModelField',
      desc: '',
      args: [],
    );
  }

  /// `RegisteredDate`
  String get pageEntitiesTerminalDeviceRegisteredDateField {
    return Intl.message(
      'RegisteredDate',
      name: 'pageEntitiesTerminalDeviceRegisteredDateField',
      desc: '',
      args: [],
    );
  }

  /// `Imei`
  String get pageEntitiesTerminalDeviceImeiField {
    return Intl.message(
      'Imei',
      name: 'pageEntitiesTerminalDeviceImeiField',
      desc: '',
      args: [],
    );
  }

  /// `SimNumber`
  String get pageEntitiesTerminalDeviceSimNumberField {
    return Intl.message(
      'SimNumber',
      name: 'pageEntitiesTerminalDeviceSimNumberField',
      desc: '',
      args: [],
    );
  }

  /// `UserStartedUsingFrom`
  String get pageEntitiesTerminalDeviceUserStartedUsingFromField {
    return Intl.message(
      'UserStartedUsingFrom',
      name: 'pageEntitiesTerminalDeviceUserStartedUsingFromField',
      desc: '',
      args: [],
    );
  }

  /// `DeviceOnLocationFrom`
  String get pageEntitiesTerminalDeviceDeviceOnLocationFromField {
    return Intl.message(
      'DeviceOnLocationFrom',
      name: 'pageEntitiesTerminalDeviceDeviceOnLocationFromField',
      desc: '',
      args: [],
    );
  }

  /// `OperatingSystem`
  String get pageEntitiesTerminalDeviceOperatingSystemField {
    return Intl.message(
      'OperatingSystem',
      name: 'pageEntitiesTerminalDeviceOperatingSystemField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesTerminalDeviceNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesTerminalDeviceNoteField',
      desc: '',
      args: [],
    );
  }

  /// `OwnerEntityId`
  String get pageEntitiesTerminalDeviceOwnerEntityIdField {
    return Intl.message(
      'OwnerEntityId',
      name: 'pageEntitiesTerminalDeviceOwnerEntityIdField',
      desc: '',
      args: [],
    );
  }

  /// `OwnerEntityName`
  String get pageEntitiesTerminalDeviceOwnerEntityNameField {
    return Intl.message(
      'OwnerEntityName',
      name: 'pageEntitiesTerminalDeviceOwnerEntityNameField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesTerminalDeviceCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesTerminalDeviceCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesTerminalDeviceLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesTerminalDeviceLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesTerminalDeviceClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesTerminalDeviceClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesTerminalDeviceHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesTerminalDeviceHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Timesheets list`
  String get pageEntitiesTimesheetListTitle {
    return Intl.message(
      'Timesheets list',
      name: 'pageEntitiesTimesheetListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Timesheets`
  String get pageEntitiesTimesheetUpdateTitle {
    return Intl.message(
      'Edit Timesheets',
      name: 'pageEntitiesTimesheetUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Timesheets`
  String get pageEntitiesTimesheetCreateTitle {
    return Intl.message(
      'Create Timesheets',
      name: 'pageEntitiesTimesheetCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Timesheets View`
  String get pageEntitiesTimesheetViewTitle {
    return Intl.message(
      'Timesheets View',
      name: 'pageEntitiesTimesheetViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Timesheets`
  String get pageEntitiesTimesheetDeletePopupTitle {
    return Intl.message(
      'Delete Timesheets',
      name: 'pageEntitiesTimesheetDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Timesheet deleted successfuly`
  String get pageEntitiesTimesheetDeleteOk {
    return Intl.message(
      'Timesheet deleted successfuly',
      name: 'pageEntitiesTimesheetDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `Description`
  String get pageEntitiesTimesheetDescriptionField {
    return Intl.message(
      'Description',
      name: 'pageEntitiesTimesheetDescriptionField',
      desc: '',
      args: [],
    );
  }

  /// `TimesheetDate`
  String get pageEntitiesTimesheetTimesheetDateField {
    return Intl.message(
      'TimesheetDate',
      name: 'pageEntitiesTimesheetTimesheetDateField',
      desc: '',
      args: [],
    );
  }

  /// `StartTime`
  String get pageEntitiesTimesheetStartTimeField {
    return Intl.message(
      'StartTime',
      name: 'pageEntitiesTimesheetStartTimeField',
      desc: '',
      args: [],
    );
  }

  /// `EndTime`
  String get pageEntitiesTimesheetEndTimeField {
    return Intl.message(
      'EndTime',
      name: 'pageEntitiesTimesheetEndTimeField',
      desc: '',
      args: [],
    );
  }

  /// `HoursWorked`
  String get pageEntitiesTimesheetHoursWorkedField {
    return Intl.message(
      'HoursWorked',
      name: 'pageEntitiesTimesheetHoursWorkedField',
      desc: '',
      args: [],
    );
  }

  /// `BreakStartTime`
  String get pageEntitiesTimesheetBreakStartTimeField {
    return Intl.message(
      'BreakStartTime',
      name: 'pageEntitiesTimesheetBreakStartTimeField',
      desc: '',
      args: [],
    );
  }

  /// `BreakEndTime`
  String get pageEntitiesTimesheetBreakEndTimeField {
    return Intl.message(
      'BreakEndTime',
      name: 'pageEntitiesTimesheetBreakEndTimeField',
      desc: '',
      args: [],
    );
  }

  /// `Note`
  String get pageEntitiesTimesheetNoteField {
    return Intl.message(
      'Note',
      name: 'pageEntitiesTimesheetNoteField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesTimesheetCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesTimesheetCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesTimesheetLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesTimesheetLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesTimesheetClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesTimesheetClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesTimesheetHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesTimesheetHasExtraDataField',
      desc: '',
      args: [],
    );
  }

  /// `Travels list`
  String get pageEntitiesTravelListTitle {
    return Intl.message(
      'Travels list',
      name: 'pageEntitiesTravelListTitle',
      desc: '',
      args: [],
    );
  }

  /// `Edit Travels`
  String get pageEntitiesTravelUpdateTitle {
    return Intl.message(
      'Edit Travels',
      name: 'pageEntitiesTravelUpdateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Create Travels`
  String get pageEntitiesTravelCreateTitle {
    return Intl.message(
      'Create Travels',
      name: 'pageEntitiesTravelCreateTitle',
      desc: '',
      args: [],
    );
  }

  /// `Travels View`
  String get pageEntitiesTravelViewTitle {
    return Intl.message(
      'Travels View',
      name: 'pageEntitiesTravelViewTitle',
      desc: '',
      args: [],
    );
  }

  /// `Delete Travels`
  String get pageEntitiesTravelDeletePopupTitle {
    return Intl.message(
      'Delete Travels',
      name: 'pageEntitiesTravelDeletePopupTitle',
      desc: '',
      args: [],
    );
  }

  /// `Travel deleted successfuly`
  String get pageEntitiesTravelDeleteOk {
    return Intl.message(
      'Travel deleted successfuly',
      name: 'pageEntitiesTravelDeleteOk',
      desc: '',
      args: [],
    );
  }

  /// `TravelMode`
  String get pageEntitiesTravelTravelModeField {
    return Intl.message(
      'TravelMode',
      name: 'pageEntitiesTravelTravelModeField',
      desc: '',
      args: [],
    );
  }

  /// `DistanceToDestination`
  String get pageEntitiesTravelDistanceToDestinationField {
    return Intl.message(
      'DistanceToDestination',
      name: 'pageEntitiesTravelDistanceToDestinationField',
      desc: '',
      args: [],
    );
  }

  /// `TimeToDestination`
  String get pageEntitiesTravelTimeToDestinationField {
    return Intl.message(
      'TimeToDestination',
      name: 'pageEntitiesTravelTimeToDestinationField',
      desc: '',
      args: [],
    );
  }

  /// `ActualDistanceRequired`
  String get pageEntitiesTravelActualDistanceRequiredField {
    return Intl.message(
      'ActualDistanceRequired',
      name: 'pageEntitiesTravelActualDistanceRequiredField',
      desc: '',
      args: [],
    );
  }

  /// `ActualTimeRequired`
  String get pageEntitiesTravelActualTimeRequiredField {
    return Intl.message(
      'ActualTimeRequired',
      name: 'pageEntitiesTravelActualTimeRequiredField',
      desc: '',
      args: [],
    );
  }

  /// `TravelStatus`
  String get pageEntitiesTravelTravelStatusField {
    return Intl.message(
      'TravelStatus',
      name: 'pageEntitiesTravelTravelStatusField',
      desc: '',
      args: [],
    );
  }

  /// `CreatedDate`
  String get pageEntitiesTravelCreatedDateField {
    return Intl.message(
      'CreatedDate',
      name: 'pageEntitiesTravelCreatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `LastUpdatedDate`
  String get pageEntitiesTravelLastUpdatedDateField {
    return Intl.message(
      'LastUpdatedDate',
      name: 'pageEntitiesTravelLastUpdatedDateField',
      desc: '',
      args: [],
    );
  }

  /// `ClientId`
  String get pageEntitiesTravelClientIdField {
    return Intl.message(
      'ClientId',
      name: 'pageEntitiesTravelClientIdField',
      desc: '',
      args: [],
    );
  }

  /// `HasExtraData`
  String get pageEntitiesTravelHasExtraDataField {
    return Intl.message(
      'HasExtraData',
      name: 'pageEntitiesTravelHasExtraDataField',
      desc: '',
      args: [],
    );
  }
}

class AppLocalizationDelegate extends LocalizationsDelegate<S> {
  const AppLocalizationDelegate();

  List<Locale> get supportedLocales {
    return const <Locale>[
      Locale.fromSubtags(languageCode: 'en'),
      Locale.fromSubtags(languageCode: 'fr'),
    ];
  }

  @override
  bool isSupported(Locale locale) => _isSupported(locale);
  @override
  Future<S> load(Locale locale) => S.load(locale);
  @override
  bool shouldReload(AppLocalizationDelegate old) => false;

  bool _isSupported(Locale locale) {
    if (locale != null) {
      for (var supportedLocale in supportedLocales) {
        if (supportedLocale.languageCode == locale.languageCode) {
          return true;
        }
      }
    }
    return false;
  }
}