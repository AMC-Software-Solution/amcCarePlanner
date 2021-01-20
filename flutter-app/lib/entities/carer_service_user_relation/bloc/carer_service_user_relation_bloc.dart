import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/carer_service_user_relation/carer_service_user_relation_model.dart';
import 'package:amcCarePlanner/entities/carer_service_user_relation/carer_service_user_relation_repository.dart';
import 'package:amcCarePlanner/entities/carer_service_user_relation/bloc/carer_service_user_relation_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'carer_service_user_relation_events.dart';
part 'carer_service_user_relation_state.dart';

class CarerServiceUserRelationBloc extends Bloc<CarerServiceUserRelationEvent, CarerServiceUserRelationState> {
  final CarerServiceUserRelationRepository _carerServiceUserRelationRepository;

  final reasonController = TextEditingController();
  final countController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  CarerServiceUserRelationBloc({@required CarerServiceUserRelationRepository carerServiceUserRelationRepository}) : assert(carerServiceUserRelationRepository != null),
        _carerServiceUserRelationRepository = carerServiceUserRelationRepository, 
  super(CarerServiceUserRelationState(null,null,));

  @override
  void onTransition(Transition<CarerServiceUserRelationEvent, CarerServiceUserRelationState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<CarerServiceUserRelationState> mapEventToState(CarerServiceUserRelationEvent event) async* {
    if (event is InitCarerServiceUserRelationList) {
      yield* onInitList(event);
    } else if (event is CarerServiceUserRelationFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadCarerServiceUserRelationByIdForEdit) {
      yield* onLoadCarerServiceUserRelationIdForEdit(event);
    } else if (event is DeleteCarerServiceUserRelationById) {
      yield* onDeleteCarerServiceUserRelationId(event);
    } else if (event is LoadCarerServiceUserRelationByIdForView) {
      yield* onLoadCarerServiceUserRelationIdForView(event);
    }else if (event is ReasonChanged){
      yield* onReasonChange(event);
    }else if (event is CountChanged){
      yield* onCountChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<CarerServiceUserRelationState> onInitList(InitCarerServiceUserRelationList event) async* {
    yield this.state.copyWith(carerServiceUserRelationStatusUI: CarerServiceUserRelationStatusUI.loading);
    List<CarerServiceUserRelation> carerServiceUserRelations = await _carerServiceUserRelationRepository.getAllCarerServiceUserRelations();
    yield this.state.copyWith(carerServiceUserRelations: carerServiceUserRelations, carerServiceUserRelationStatusUI: CarerServiceUserRelationStatusUI.done);
  }

  Stream<CarerServiceUserRelationState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        CarerServiceUserRelation result;
        if(this.state.editMode) {
          CarerServiceUserRelation newCarerServiceUserRelation = CarerServiceUserRelation(state.loadedCarerServiceUserRelation.id,
            this.state.reason.value,  
            this.state.count.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _carerServiceUserRelationRepository.update(newCarerServiceUserRelation);
        } else {
          CarerServiceUserRelation newCarerServiceUserRelation = CarerServiceUserRelation(null,
            this.state.reason.value,  
            this.state.count.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _carerServiceUserRelationRepository.create(newCarerServiceUserRelation);
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

  Stream<CarerServiceUserRelationState> onLoadCarerServiceUserRelationIdForEdit(LoadCarerServiceUserRelationByIdForEdit event) async* {
    yield this.state.copyWith(carerServiceUserRelationStatusUI: CarerServiceUserRelationStatusUI.loading);
    CarerServiceUserRelation loadedCarerServiceUserRelation = await _carerServiceUserRelationRepository.getCarerServiceUserRelation(event.id);

    final reason = ReasonInput.dirty(loadedCarerServiceUserRelation?.reason != null ? loadedCarerServiceUserRelation.reason: '');
    final count = CountInput.dirty(loadedCarerServiceUserRelation?.count != null ? loadedCarerServiceUserRelation.count: 0);
    final createdDate = CreatedDateInput.dirty(loadedCarerServiceUserRelation?.createdDate != null ? loadedCarerServiceUserRelation.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedCarerServiceUserRelation?.lastUpdatedDate != null ? loadedCarerServiceUserRelation.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedCarerServiceUserRelation?.clientId != null ? loadedCarerServiceUserRelation.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedCarerServiceUserRelation?.hasExtraData != null ? loadedCarerServiceUserRelation.hasExtraData: false);

    yield this.state.copyWith(loadedCarerServiceUserRelation: loadedCarerServiceUserRelation, editMode: true,
      reason: reason,
      count: count,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    carerServiceUserRelationStatusUI: CarerServiceUserRelationStatusUI.done);

    reasonController.text = loadedCarerServiceUserRelation.reason;
    countController.text = loadedCarerServiceUserRelation.count.toString();
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedCarerServiceUserRelation?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedCarerServiceUserRelation?.lastUpdatedDate);
    clientIdController.text = loadedCarerServiceUserRelation.clientId.toString();
  }

  Stream<CarerServiceUserRelationState> onDeleteCarerServiceUserRelationId(DeleteCarerServiceUserRelationById event) async* {
    try {
      await _carerServiceUserRelationRepository.delete(event.id);
      this.add(InitCarerServiceUserRelationList());
      yield this.state.copyWith(deleteStatus: CarerServiceUserRelationDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: CarerServiceUserRelationDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: CarerServiceUserRelationDeleteStatus.none);
  }

  Stream<CarerServiceUserRelationState> onLoadCarerServiceUserRelationIdForView(LoadCarerServiceUserRelationByIdForView event) async* {
    yield this.state.copyWith(carerServiceUserRelationStatusUI: CarerServiceUserRelationStatusUI.loading);
    try {
      CarerServiceUserRelation loadedCarerServiceUserRelation = await _carerServiceUserRelationRepository.getCarerServiceUserRelation(event.id);
      yield this.state.copyWith(loadedCarerServiceUserRelation: loadedCarerServiceUserRelation, carerServiceUserRelationStatusUI: CarerServiceUserRelationStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedCarerServiceUserRelation: null, carerServiceUserRelationStatusUI: CarerServiceUserRelationStatusUI.error);
    }
  }


  Stream<CarerServiceUserRelationState> onReasonChange(ReasonChanged event) async* {
    final reason = ReasonInput.dirty(event.reason);
    yield this.state.copyWith(
      reason: reason,
      formStatus: Formz.validate([
        reason,
      this.state.count,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CarerServiceUserRelationState> onCountChange(CountChanged event) async* {
    final count = CountInput.dirty(event.count);
    yield this.state.copyWith(
      count: count,
      formStatus: Formz.validate([
      this.state.reason,
        count,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CarerServiceUserRelationState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.reason,
      this.state.count,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CarerServiceUserRelationState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.reason,
      this.state.count,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CarerServiceUserRelationState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.reason,
      this.state.count,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CarerServiceUserRelationState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.reason,
      this.state.count,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    reasonController.dispose();
    countController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}