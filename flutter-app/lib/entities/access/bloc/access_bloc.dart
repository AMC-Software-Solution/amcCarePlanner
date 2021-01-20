import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/access/access_model.dart';
import 'package:amcCarePlanner/entities/access/access_repository.dart';
import 'package:amcCarePlanner/entities/access/bloc/access_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'access_events.dart';
part 'access_state.dart';

class AccessBloc extends Bloc<AccessEvent, AccessState> {
  final AccessRepository _accessRepository;

  final keySafeNumberController = TextEditingController();
  final accessDetailsController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  AccessBloc({@required AccessRepository accessRepository}) : assert(accessRepository != null),
        _accessRepository = accessRepository, 
  super(AccessState(null,null,));

  @override
  void onTransition(Transition<AccessEvent, AccessState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<AccessState> mapEventToState(AccessEvent event) async* {
    if (event is InitAccessList) {
      yield* onInitList(event);
    } else if (event is AccessFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadAccessByIdForEdit) {
      yield* onLoadAccessIdForEdit(event);
    } else if (event is DeleteAccessById) {
      yield* onDeleteAccessId(event);
    } else if (event is LoadAccessByIdForView) {
      yield* onLoadAccessIdForView(event);
    }else if (event is KeySafeNumberChanged){
      yield* onKeySafeNumberChange(event);
    }else if (event is AccessDetailsChanged){
      yield* onAccessDetailsChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<AccessState> onInitList(InitAccessList event) async* {
    yield this.state.copyWith(accessStatusUI: AccessStatusUI.loading);
    List<Access> accesses = await _accessRepository.getAllAccesses();
    yield this.state.copyWith(accesses: accesses, accessStatusUI: AccessStatusUI.done);
  }

  Stream<AccessState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Access result;
        if(this.state.editMode) {
          Access newAccess = Access(state.loadedAccess.id,
            this.state.keySafeNumber.value,  
            this.state.accessDetails.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _accessRepository.update(newAccess);
        } else {
          Access newAccess = Access(null,
            this.state.keySafeNumber.value,  
            this.state.accessDetails.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _accessRepository.create(newAccess);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<AccessState> onLoadAccessIdForEdit(LoadAccessByIdForEdit event) async* {
    yield this.state.copyWith(accessStatusUI: AccessStatusUI.loading);
    Access loadedAccess = await _accessRepository.getAccess(event.id);

    final keySafeNumber = KeySafeNumberInput.dirty(loadedAccess?.keySafeNumber != null ? loadedAccess.keySafeNumber: '');
    final accessDetails = AccessDetailsInput.dirty(loadedAccess?.accessDetails != null ? loadedAccess.accessDetails: '');
    final createdDate = CreatedDateInput.dirty(loadedAccess?.createdDate != null ? loadedAccess.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedAccess?.lastUpdatedDate != null ? loadedAccess.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedAccess?.clientId != null ? loadedAccess.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedAccess?.hasExtraData != null ? loadedAccess.hasExtraData: false);

    yield this.state.copyWith(loadedAccess: loadedAccess, editMode: true,
      keySafeNumber: keySafeNumber,
      accessDetails: accessDetails,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    accessStatusUI: AccessStatusUI.done);

    keySafeNumberController.text = loadedAccess.keySafeNumber;
    accessDetailsController.text = loadedAccess.accessDetails;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedAccess?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedAccess?.lastUpdatedDate);
    clientIdController.text = loadedAccess.clientId.toString();
  }

  Stream<AccessState> onDeleteAccessId(DeleteAccessById event) async* {
    try {
      await _accessRepository.delete(event.id);
      this.add(InitAccessList());
      yield this.state.copyWith(deleteStatus: AccessDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: AccessDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: AccessDeleteStatus.none);
  }

  Stream<AccessState> onLoadAccessIdForView(LoadAccessByIdForView event) async* {
    yield this.state.copyWith(accessStatusUI: AccessStatusUI.loading);
    try {
      Access loadedAccess = await _accessRepository.getAccess(event.id);
      yield this.state.copyWith(loadedAccess: loadedAccess, accessStatusUI: AccessStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedAccess: null, accessStatusUI: AccessStatusUI.error);
    }
  }


  Stream<AccessState> onKeySafeNumberChange(KeySafeNumberChanged event) async* {
    final keySafeNumber = KeySafeNumberInput.dirty(event.keySafeNumber);
    yield this.state.copyWith(
      keySafeNumber: keySafeNumber,
      formStatus: Formz.validate([
        keySafeNumber,
      this.state.accessDetails,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AccessState> onAccessDetailsChange(AccessDetailsChanged event) async* {
    final accessDetails = AccessDetailsInput.dirty(event.accessDetails);
    yield this.state.copyWith(
      accessDetails: accessDetails,
      formStatus: Formz.validate([
      this.state.keySafeNumber,
        accessDetails,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AccessState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.keySafeNumber,
      this.state.accessDetails,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AccessState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.keySafeNumber,
      this.state.accessDetails,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AccessState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.keySafeNumber,
      this.state.accessDetails,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<AccessState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.keySafeNumber,
      this.state.accessDetails,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    keySafeNumberController.dispose();
    accessDetailsController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}