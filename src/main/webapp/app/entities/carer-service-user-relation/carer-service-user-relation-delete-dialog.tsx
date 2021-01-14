import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICarerServiceUserRelation } from 'app/shared/model/carer-service-user-relation.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './carer-service-user-relation.reducer';

export interface ICarerServiceUserRelationDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CarerServiceUserRelationDeleteDialog = (props: ICarerServiceUserRelationDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/carer-service-user-relation' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.carerServiceUserRelationEntity.id);
  };

  const { carerServiceUserRelationEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="carePlannerApp.carerServiceUserRelation.delete.question">
        <Translate
          contentKey="carePlannerApp.carerServiceUserRelation.delete.question"
          interpolate={{ id: carerServiceUserRelationEntity.id }}
        >
          Are you sure you want to delete this CarerServiceUserRelation?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-carerServiceUserRelation" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ carerServiceUserRelation }: IRootState) => ({
  carerServiceUserRelationEntity: carerServiceUserRelation.entity,
  updateSuccess: carerServiceUserRelation.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CarerServiceUserRelationDeleteDialog);
