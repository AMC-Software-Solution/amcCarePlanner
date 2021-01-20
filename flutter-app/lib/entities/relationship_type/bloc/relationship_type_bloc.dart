import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/relationship_type/relationship_type_model.dart';
import 'package:amcCarePlanner/entities/relationship_type/relationship_type_repository.dart';
import 'package:amcCarePlanner/entities/relationship_type/bloc/relationship_type_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'relationship_type_events.dart';
part 'relationship_type_state.dart';

class RelationshipTypeBloc extends Bloc<RelationshipTypeEvent, RelationshipTypeState> {
  final RelationshipTypeRepository _relationshipTypeRepository;

  final relationTypeController = TextEditingController();
  final descriptionController = TextEditingController();
  final clientIdController = TextEditingController();

  RelationshipTypeBloc({@required RelationshipTypeRepository relationshipTypeRepository}) : assert(relationshipTypeRepository != null),
        _relationshipTypeRepository = relationshipTypeRepository, 
  super(RelationshipTypeState());

  @override
  void onTransition(Transition<RelationshipTypeEvent, RelationshipTypeState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<RelationshipTypeState> mapEventToState(RelationshipTypeEvent event) async* {
    if (event is InitRelationshipTypeList) {
      yield* onInitList(event);
    } else if (event is RelationshipTypeFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadRelationshipTypeByIdForEdit) {
      yield* onLoadRelationshipTypeIdForEdit(event);
    } else if (event is DeleteRelationshipTypeById) {
      yield* onDeleteRelationshipTypeId(event);
    } else if (event is LoadRelationshipTypeByIdForView) {
      yield* onLoadRelationshipTypeIdForView(event);
    }else if (event is RelationTypeChanged){
      yield* onRelationTypeChange(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<RelationshipTypeState> onInitList(InitRelationshipTypeList event) async* {
    yield this.state.copyWith(relationshipTypeStatusUI: RelationshipTypeStatusUI.loading);
    List<RelationshipType> relationshipTypes = await _relationshipTypeRepository.getAllRelationshipTypes();
    yield this.state.copyWith(relationshipTypes: relationshipTypes, relationshipTypeStatusUI: RelationshipTypeStatusUI.done);
  }

  Stream<RelationshipTypeState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        RelationshipType result;
        if(this.state.editMode) {
          RelationshipType newRelationshipType = RelationshipType(state.loadedRelationshipType.id,
            this.state.relationType.value,  
            this.state.description.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _relationshipTypeRepository.update(newRelationshipType);
        } else {
          RelationshipType newRelationshipType = RelationshipType(null,
            this.state.relationType.value,  
            this.state.description.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _relationshipTypeRepository.create(newRelationshipType);
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

  Stream<RelationshipTypeState> onLoadRelationshipTypeIdForEdit(LoadRelationshipTypeByIdForEdit event) async* {
    yield this.state.copyWith(relationshipTypeStatusUI: RelationshipTypeStatusUI.loading);
    RelationshipType loadedRelationshipType = await _relationshipTypeRepository.getRelationshipType(event.id);

    final relationType = RelationTypeInput.dirty(loadedRelationshipType?.relationType != null ? loadedRelationshipType.relationType: '');
    final description = DescriptionInput.dirty(loadedRelationshipType?.description != null ? loadedRelationshipType.description: '');
    final clientId = ClientIdInput.dirty(loadedRelationshipType?.clientId != null ? loadedRelationshipType.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedRelationshipType?.hasExtraData != null ? loadedRelationshipType.hasExtraData: false);

    yield this.state.copyWith(loadedRelationshipType: loadedRelationshipType, editMode: true,
      relationType: relationType,
      description: description,
      clientId: clientId,
      hasExtraData: hasExtraData,
    relationshipTypeStatusUI: RelationshipTypeStatusUI.done);

    relationTypeController.text = loadedRelationshipType.relationType;
    descriptionController.text = loadedRelationshipType.description;
    clientIdController.text = loadedRelationshipType.clientId.toString();
  }

  Stream<RelationshipTypeState> onDeleteRelationshipTypeId(DeleteRelationshipTypeById event) async* {
    try {
      await _relationshipTypeRepository.delete(event.id);
      this.add(InitRelationshipTypeList());
      yield this.state.copyWith(deleteStatus: RelationshipTypeDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: RelationshipTypeDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: RelationshipTypeDeleteStatus.none);
  }

  Stream<RelationshipTypeState> onLoadRelationshipTypeIdForView(LoadRelationshipTypeByIdForView event) async* {
    yield this.state.copyWith(relationshipTypeStatusUI: RelationshipTypeStatusUI.loading);
    try {
      RelationshipType loadedRelationshipType = await _relationshipTypeRepository.getRelationshipType(event.id);
      yield this.state.copyWith(loadedRelationshipType: loadedRelationshipType, relationshipTypeStatusUI: RelationshipTypeStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedRelationshipType: null, relationshipTypeStatusUI: RelationshipTypeStatusUI.error);
    }
  }


  Stream<RelationshipTypeState> onRelationTypeChange(RelationTypeChanged event) async* {
    final relationType = RelationTypeInput.dirty(event.relationType);
    yield this.state.copyWith(
      relationType: relationType,
      formStatus: Formz.validate([
        relationType,
      this.state.description,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<RelationshipTypeState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.relationType,
        description,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<RelationshipTypeState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.relationType,
      this.state.description,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<RelationshipTypeState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.relationType,
      this.state.description,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    relationTypeController.dispose();
    descriptionController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}