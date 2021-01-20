import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/power_of_attorney/power_of_attorney_model.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/power_of_attorney_repository.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/bloc/power_of_attorney_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'power_of_attorney_events.dart';
part 'power_of_attorney_state.dart';

class PowerOfAttorneyBloc extends Bloc<PowerOfAttorneyEvent, PowerOfAttorneyState> {
  final PowerOfAttorneyRepository _powerOfAttorneyRepository;

  final healthAndWelfareNameController = TextEditingController();
  final propertyAndFinAffairsNameController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  PowerOfAttorneyBloc({@required PowerOfAttorneyRepository powerOfAttorneyRepository}) : assert(powerOfAttorneyRepository != null),
        _powerOfAttorneyRepository = powerOfAttorneyRepository, 
  super(PowerOfAttorneyState(null,null,));

  @override
  void onTransition(Transition<PowerOfAttorneyEvent, PowerOfAttorneyState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<PowerOfAttorneyState> mapEventToState(PowerOfAttorneyEvent event) async* {
    if (event is InitPowerOfAttorneyList) {
      yield* onInitList(event);
    } else if (event is PowerOfAttorneyFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadPowerOfAttorneyByIdForEdit) {
      yield* onLoadPowerOfAttorneyIdForEdit(event);
    } else if (event is DeletePowerOfAttorneyById) {
      yield* onDeletePowerOfAttorneyId(event);
    } else if (event is LoadPowerOfAttorneyByIdForView) {
      yield* onLoadPowerOfAttorneyIdForView(event);
    }else if (event is PowerOfAttorneyConsentChanged){
      yield* onPowerOfAttorneyConsentChange(event);
    }else if (event is HealthAndWelfareChanged){
      yield* onHealthAndWelfareChange(event);
    }else if (event is HealthAndWelfareNameChanged){
      yield* onHealthAndWelfareNameChange(event);
    }else if (event is PropertyAndFinAffairsChanged){
      yield* onPropertyAndFinAffairsChange(event);
    }else if (event is PropertyAndFinAffairsNameChanged){
      yield* onPropertyAndFinAffairsNameChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<PowerOfAttorneyState> onInitList(InitPowerOfAttorneyList event) async* {
    yield this.state.copyWith(powerOfAttorneyStatusUI: PowerOfAttorneyStatusUI.loading);
    List<PowerOfAttorney> powerOfAttorneys = await _powerOfAttorneyRepository.getAllPowerOfAttorneys();
    yield this.state.copyWith(powerOfAttorneys: powerOfAttorneys, powerOfAttorneyStatusUI: PowerOfAttorneyStatusUI.done);
  }

  Stream<PowerOfAttorneyState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        PowerOfAttorney result;
        if(this.state.editMode) {
          PowerOfAttorney newPowerOfAttorney = PowerOfAttorney(state.loadedPowerOfAttorney.id,
            this.state.powerOfAttorneyConsent.value,  
            this.state.healthAndWelfare.value,  
            this.state.healthAndWelfareName.value,  
            this.state.propertyAndFinAffairs.value,  
            this.state.propertyAndFinAffairsName.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _powerOfAttorneyRepository.update(newPowerOfAttorney);
        } else {
          PowerOfAttorney newPowerOfAttorney = PowerOfAttorney(null,
            this.state.powerOfAttorneyConsent.value,  
            this.state.healthAndWelfare.value,  
            this.state.healthAndWelfareName.value,  
            this.state.propertyAndFinAffairs.value,  
            this.state.propertyAndFinAffairsName.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _powerOfAttorneyRepository.create(newPowerOfAttorney);
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

  Stream<PowerOfAttorneyState> onLoadPowerOfAttorneyIdForEdit(LoadPowerOfAttorneyByIdForEdit event) async* {
    yield this.state.copyWith(powerOfAttorneyStatusUI: PowerOfAttorneyStatusUI.loading);
    PowerOfAttorney loadedPowerOfAttorney = await _powerOfAttorneyRepository.getPowerOfAttorney(event.id);

    final powerOfAttorneyConsent = PowerOfAttorneyConsentInput.dirty(loadedPowerOfAttorney?.powerOfAttorneyConsent != null ? loadedPowerOfAttorney.powerOfAttorneyConsent: false);
    final healthAndWelfare = HealthAndWelfareInput.dirty(loadedPowerOfAttorney?.healthAndWelfare != null ? loadedPowerOfAttorney.healthAndWelfare: false);
    final healthAndWelfareName = HealthAndWelfareNameInput.dirty(loadedPowerOfAttorney?.healthAndWelfareName != null ? loadedPowerOfAttorney.healthAndWelfareName: '');
    final propertyAndFinAffairs = PropertyAndFinAffairsInput.dirty(loadedPowerOfAttorney?.propertyAndFinAffairs != null ? loadedPowerOfAttorney.propertyAndFinAffairs: false);
    final propertyAndFinAffairsName = PropertyAndFinAffairsNameInput.dirty(loadedPowerOfAttorney?.propertyAndFinAffairsName != null ? loadedPowerOfAttorney.propertyAndFinAffairsName: '');
    final createdDate = CreatedDateInput.dirty(loadedPowerOfAttorney?.createdDate != null ? loadedPowerOfAttorney.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedPowerOfAttorney?.lastUpdatedDate != null ? loadedPowerOfAttorney.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedPowerOfAttorney?.clientId != null ? loadedPowerOfAttorney.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedPowerOfAttorney?.hasExtraData != null ? loadedPowerOfAttorney.hasExtraData: false);

    yield this.state.copyWith(loadedPowerOfAttorney: loadedPowerOfAttorney, editMode: true,
      powerOfAttorneyConsent: powerOfAttorneyConsent,
      healthAndWelfare: healthAndWelfare,
      healthAndWelfareName: healthAndWelfareName,
      propertyAndFinAffairs: propertyAndFinAffairs,
      propertyAndFinAffairsName: propertyAndFinAffairsName,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    powerOfAttorneyStatusUI: PowerOfAttorneyStatusUI.done);

    healthAndWelfareNameController.text = loadedPowerOfAttorney.healthAndWelfareName;
    propertyAndFinAffairsNameController.text = loadedPowerOfAttorney.propertyAndFinAffairsName;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedPowerOfAttorney?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedPowerOfAttorney?.lastUpdatedDate);
    clientIdController.text = loadedPowerOfAttorney.clientId.toString();
  }

  Stream<PowerOfAttorneyState> onDeletePowerOfAttorneyId(DeletePowerOfAttorneyById event) async* {
    try {
      await _powerOfAttorneyRepository.delete(event.id);
      this.add(InitPowerOfAttorneyList());
      yield this.state.copyWith(deleteStatus: PowerOfAttorneyDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: PowerOfAttorneyDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: PowerOfAttorneyDeleteStatus.none);
  }

  Stream<PowerOfAttorneyState> onLoadPowerOfAttorneyIdForView(LoadPowerOfAttorneyByIdForView event) async* {
    yield this.state.copyWith(powerOfAttorneyStatusUI: PowerOfAttorneyStatusUI.loading);
    try {
      PowerOfAttorney loadedPowerOfAttorney = await _powerOfAttorneyRepository.getPowerOfAttorney(event.id);
      yield this.state.copyWith(loadedPowerOfAttorney: loadedPowerOfAttorney, powerOfAttorneyStatusUI: PowerOfAttorneyStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedPowerOfAttorney: null, powerOfAttorneyStatusUI: PowerOfAttorneyStatusUI.error);
    }
  }


  Stream<PowerOfAttorneyState> onPowerOfAttorneyConsentChange(PowerOfAttorneyConsentChanged event) async* {
    final powerOfAttorneyConsent = PowerOfAttorneyConsentInput.dirty(event.powerOfAttorneyConsent);
    yield this.state.copyWith(
      powerOfAttorneyConsent: powerOfAttorneyConsent,
      formStatus: Formz.validate([
        powerOfAttorneyConsent,
      this.state.healthAndWelfare,
      this.state.healthAndWelfareName,
      this.state.propertyAndFinAffairs,
      this.state.propertyAndFinAffairsName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PowerOfAttorneyState> onHealthAndWelfareChange(HealthAndWelfareChanged event) async* {
    final healthAndWelfare = HealthAndWelfareInput.dirty(event.healthAndWelfare);
    yield this.state.copyWith(
      healthAndWelfare: healthAndWelfare,
      formStatus: Formz.validate([
      this.state.powerOfAttorneyConsent,
        healthAndWelfare,
      this.state.healthAndWelfareName,
      this.state.propertyAndFinAffairs,
      this.state.propertyAndFinAffairsName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PowerOfAttorneyState> onHealthAndWelfareNameChange(HealthAndWelfareNameChanged event) async* {
    final healthAndWelfareName = HealthAndWelfareNameInput.dirty(event.healthAndWelfareName);
    yield this.state.copyWith(
      healthAndWelfareName: healthAndWelfareName,
      formStatus: Formz.validate([
      this.state.powerOfAttorneyConsent,
      this.state.healthAndWelfare,
        healthAndWelfareName,
      this.state.propertyAndFinAffairs,
      this.state.propertyAndFinAffairsName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PowerOfAttorneyState> onPropertyAndFinAffairsChange(PropertyAndFinAffairsChanged event) async* {
    final propertyAndFinAffairs = PropertyAndFinAffairsInput.dirty(event.propertyAndFinAffairs);
    yield this.state.copyWith(
      propertyAndFinAffairs: propertyAndFinAffairs,
      formStatus: Formz.validate([
      this.state.powerOfAttorneyConsent,
      this.state.healthAndWelfare,
      this.state.healthAndWelfareName,
        propertyAndFinAffairs,
      this.state.propertyAndFinAffairsName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PowerOfAttorneyState> onPropertyAndFinAffairsNameChange(PropertyAndFinAffairsNameChanged event) async* {
    final propertyAndFinAffairsName = PropertyAndFinAffairsNameInput.dirty(event.propertyAndFinAffairsName);
    yield this.state.copyWith(
      propertyAndFinAffairsName: propertyAndFinAffairsName,
      formStatus: Formz.validate([
      this.state.powerOfAttorneyConsent,
      this.state.healthAndWelfare,
      this.state.healthAndWelfareName,
      this.state.propertyAndFinAffairs,
        propertyAndFinAffairsName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PowerOfAttorneyState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.powerOfAttorneyConsent,
      this.state.healthAndWelfare,
      this.state.healthAndWelfareName,
      this.state.propertyAndFinAffairs,
      this.state.propertyAndFinAffairsName,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PowerOfAttorneyState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.powerOfAttorneyConsent,
      this.state.healthAndWelfare,
      this.state.healthAndWelfareName,
      this.state.propertyAndFinAffairs,
      this.state.propertyAndFinAffairsName,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PowerOfAttorneyState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.powerOfAttorneyConsent,
      this.state.healthAndWelfare,
      this.state.healthAndWelfareName,
      this.state.propertyAndFinAffairs,
      this.state.propertyAndFinAffairsName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PowerOfAttorneyState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.powerOfAttorneyConsent,
      this.state.healthAndWelfare,
      this.state.healthAndWelfareName,
      this.state.propertyAndFinAffairs,
      this.state.propertyAndFinAffairsName,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    healthAndWelfareNameController.dispose();
    propertyAndFinAffairsNameController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}