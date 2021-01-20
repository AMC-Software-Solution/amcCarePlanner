import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/travel/travel_model.dart';
import 'package:amcCarePlanner/entities/travel/travel_repository.dart';
import 'package:amcCarePlanner/entities/travel/bloc/travel_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'travel_events.dart';
part 'travel_state.dart';

class TravelBloc extends Bloc<TravelEvent, TravelState> {
  final TravelRepository _travelRepository;

  final distanceToDestinationController = TextEditingController();
  final timeToDestinationController = TextEditingController();
  final actualDistanceRequiredController = TextEditingController();
  final actualTimeRequiredController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  TravelBloc({@required TravelRepository travelRepository}) : assert(travelRepository != null),
        _travelRepository = travelRepository, 
  super(TravelState(null,null,));

  @override
  void onTransition(Transition<TravelEvent, TravelState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<TravelState> mapEventToState(TravelEvent event) async* {
    if (event is InitTravelList) {
      yield* onInitList(event);
    } else if (event is TravelFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadTravelByIdForEdit) {
      yield* onLoadTravelIdForEdit(event);
    } else if (event is DeleteTravelById) {
      yield* onDeleteTravelId(event);
    } else if (event is LoadTravelByIdForView) {
      yield* onLoadTravelIdForView(event);
    }else if (event is TravelModeChanged){
      yield* onTravelModeChange(event);
    }else if (event is DistanceToDestinationChanged){
      yield* onDistanceToDestinationChange(event);
    }else if (event is TimeToDestinationChanged){
      yield* onTimeToDestinationChange(event);
    }else if (event is ActualDistanceRequiredChanged){
      yield* onActualDistanceRequiredChange(event);
    }else if (event is ActualTimeRequiredChanged){
      yield* onActualTimeRequiredChange(event);
    }else if (event is TravelStatusChanged){
      yield* onTravelStatusChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<TravelState> onInitList(InitTravelList event) async* {
    yield this.state.copyWith(travelStatusUI: TravelStatusUI.loading);
    List<Travel> travels = await _travelRepository.getAllTravels();
    yield this.state.copyWith(travels: travels, travelStatusUI: TravelStatusUI.done);
  }

  Stream<TravelState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Travel result;
        if(this.state.editMode) {
          Travel newTravel = Travel(state.loadedTravel.id,
            this.state.travelMode.value,  
            this.state.distanceToDestination.value,  
            this.state.timeToDestination.value,  
            this.state.actualDistanceRequired.value,  
            this.state.actualTimeRequired.value,  
            this.state.travelStatus.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _travelRepository.update(newTravel);
        } else {
          Travel newTravel = Travel(null,
            this.state.travelMode.value,  
            this.state.distanceToDestination.value,  
            this.state.timeToDestination.value,  
            this.state.actualDistanceRequired.value,  
            this.state.actualTimeRequired.value,  
            this.state.travelStatus.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
          );

          result = await _travelRepository.create(newTravel);
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

  Stream<TravelState> onLoadTravelIdForEdit(LoadTravelByIdForEdit event) async* {
    yield this.state.copyWith(travelStatusUI: TravelStatusUI.loading);
    Travel loadedTravel = await _travelRepository.getTravel(event.id);

    final travelMode = TravelModeInput.dirty(loadedTravel?.travelMode != null ? loadedTravel.travelMode: null);
    final distanceToDestination = DistanceToDestinationInput.dirty(loadedTravel?.distanceToDestination != null ? loadedTravel.distanceToDestination: 0);
    final timeToDestination = TimeToDestinationInput.dirty(loadedTravel?.timeToDestination != null ? loadedTravel.timeToDestination: 0);
    final actualDistanceRequired = ActualDistanceRequiredInput.dirty(loadedTravel?.actualDistanceRequired != null ? loadedTravel.actualDistanceRequired: 0);
    final actualTimeRequired = ActualTimeRequiredInput.dirty(loadedTravel?.actualTimeRequired != null ? loadedTravel.actualTimeRequired: 0);
    final travelStatus = TravelStatusInput.dirty(loadedTravel?.travelStatus != null ? loadedTravel.travelStatus: null);
    final createdDate = CreatedDateInput.dirty(loadedTravel?.createdDate != null ? loadedTravel.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedTravel?.lastUpdatedDate != null ? loadedTravel.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedTravel?.clientId != null ? loadedTravel.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedTravel?.hasExtraData != null ? loadedTravel.hasExtraData: false);

    yield this.state.copyWith(loadedTravel: loadedTravel, editMode: true,
      travelMode: travelMode,
      distanceToDestination: distanceToDestination,
      timeToDestination: timeToDestination,
      actualDistanceRequired: actualDistanceRequired,
      actualTimeRequired: actualTimeRequired,
      travelStatus: travelStatus,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    travelStatusUI: TravelStatusUI.done);

    distanceToDestinationController.text = loadedTravel.distanceToDestination.toString();
    timeToDestinationController.text = loadedTravel.timeToDestination.toString();
    actualDistanceRequiredController.text = loadedTravel.actualDistanceRequired.toString();
    actualTimeRequiredController.text = loadedTravel.actualTimeRequired.toString();
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTravel?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTravel?.lastUpdatedDate);
    clientIdController.text = loadedTravel.clientId.toString();
  }

  Stream<TravelState> onDeleteTravelId(DeleteTravelById event) async* {
    try {
      await _travelRepository.delete(event.id);
      this.add(InitTravelList());
      yield this.state.copyWith(deleteStatus: TravelDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: TravelDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: TravelDeleteStatus.none);
  }

  Stream<TravelState> onLoadTravelIdForView(LoadTravelByIdForView event) async* {
    yield this.state.copyWith(travelStatusUI: TravelStatusUI.loading);
    try {
      Travel loadedTravel = await _travelRepository.getTravel(event.id);
      yield this.state.copyWith(loadedTravel: loadedTravel, travelStatusUI: TravelStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedTravel: null, travelStatusUI: TravelStatusUI.error);
    }
  }


  Stream<TravelState> onTravelModeChange(TravelModeChanged event) async* {
    final travelMode = TravelModeInput.dirty(event.travelMode);
    yield this.state.copyWith(
      travelMode: travelMode,
      formStatus: Formz.validate([
        travelMode,
      this.state.distanceToDestination,
      this.state.timeToDestination,
      this.state.actualDistanceRequired,
      this.state.actualTimeRequired,
      this.state.travelStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TravelState> onDistanceToDestinationChange(DistanceToDestinationChanged event) async* {
    final distanceToDestination = DistanceToDestinationInput.dirty(event.distanceToDestination);
    yield this.state.copyWith(
      distanceToDestination: distanceToDestination,
      formStatus: Formz.validate([
      this.state.travelMode,
        distanceToDestination,
      this.state.timeToDestination,
      this.state.actualDistanceRequired,
      this.state.actualTimeRequired,
      this.state.travelStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TravelState> onTimeToDestinationChange(TimeToDestinationChanged event) async* {
    final timeToDestination = TimeToDestinationInput.dirty(event.timeToDestination);
    yield this.state.copyWith(
      timeToDestination: timeToDestination,
      formStatus: Formz.validate([
      this.state.travelMode,
      this.state.distanceToDestination,
        timeToDestination,
      this.state.actualDistanceRequired,
      this.state.actualTimeRequired,
      this.state.travelStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TravelState> onActualDistanceRequiredChange(ActualDistanceRequiredChanged event) async* {
    final actualDistanceRequired = ActualDistanceRequiredInput.dirty(event.actualDistanceRequired);
    yield this.state.copyWith(
      actualDistanceRequired: actualDistanceRequired,
      formStatus: Formz.validate([
      this.state.travelMode,
      this.state.distanceToDestination,
      this.state.timeToDestination,
        actualDistanceRequired,
      this.state.actualTimeRequired,
      this.state.travelStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TravelState> onActualTimeRequiredChange(ActualTimeRequiredChanged event) async* {
    final actualTimeRequired = ActualTimeRequiredInput.dirty(event.actualTimeRequired);
    yield this.state.copyWith(
      actualTimeRequired: actualTimeRequired,
      formStatus: Formz.validate([
      this.state.travelMode,
      this.state.distanceToDestination,
      this.state.timeToDestination,
      this.state.actualDistanceRequired,
        actualTimeRequired,
      this.state.travelStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TravelState> onTravelStatusChange(TravelStatusChanged event) async* {
    final travelStatus = TravelStatusInput.dirty(event.travelStatus);
    yield this.state.copyWith(
      travelStatus: travelStatus,
      formStatus: Formz.validate([
      this.state.travelMode,
      this.state.distanceToDestination,
      this.state.timeToDestination,
      this.state.actualDistanceRequired,
      this.state.actualTimeRequired,
        travelStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TravelState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.travelMode,
      this.state.distanceToDestination,
      this.state.timeToDestination,
      this.state.actualDistanceRequired,
      this.state.actualTimeRequired,
      this.state.travelStatus,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TravelState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.travelMode,
      this.state.distanceToDestination,
      this.state.timeToDestination,
      this.state.actualDistanceRequired,
      this.state.actualTimeRequired,
      this.state.travelStatus,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TravelState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.travelMode,
      this.state.distanceToDestination,
      this.state.timeToDestination,
      this.state.actualDistanceRequired,
      this.state.actualTimeRequired,
      this.state.travelStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TravelState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.travelMode,
      this.state.distanceToDestination,
      this.state.timeToDestination,
      this.state.actualDistanceRequired,
      this.state.actualTimeRequired,
      this.state.travelStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    distanceToDestinationController.dispose();
    timeToDestinationController.dispose();
    actualDistanceRequiredController.dispose();
    actualTimeRequiredController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}