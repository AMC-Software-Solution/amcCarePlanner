import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './branch.reducer';
import { IBranch } from 'app/shared/model/branch.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBranchUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BranchUpdate = (props: IBranchUpdateProps) => {
  const [clientId, setClientId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { branchEntity, clients, loading, updating } = props;

  const { photo, photoContentType } = branchEntity;

  const handleClose = () => {
    props.history.push('/branch' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClients();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...branchEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="carePlannerApp.branch.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.branch.home.createOrEditLabel">Create or edit a Branch</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : branchEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="branch-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="branch-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="branch-name">
                  <Translate contentKey="carePlannerApp.branch.name">Name</Translate>
                </Label>
                <AvField
                  id="branch-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="branch-address">
                  <Translate contentKey="carePlannerApp.branch.address">Address</Translate>
                </Label>
                <AvField id="branch-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="branch-telephone">
                  <Translate contentKey="carePlannerApp.branch.telephone">Telephone</Translate>
                </Label>
                <AvField id="branch-telephone" type="text" name="telephone" />
              </AvGroup>
              <AvGroup>
                <Label id="contactNameLabel" for="branch-contactName">
                  <Translate contentKey="carePlannerApp.branch.contactName">Contact Name</Translate>
                </Label>
                <AvField id="branch-contactName" type="text" name="contactName" />
              </AvGroup>
              <AvGroup>
                <Label id="branchEmailLabel" for="branch-branchEmail">
                  <Translate contentKey="carePlannerApp.branch.branchEmail">Branch Email</Translate>
                </Label>
                <AvField id="branch-branchEmail" type="text" name="branchEmail" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="photoLabel" for="photo">
                    <Translate contentKey="carePlannerApp.branch.photo">Photo</Translate>
                  </Label>
                  <br />
                  {photo ? (
                    <div>
                      {photoContentType ? (
                        <a onClick={openFile(photoContentType, photo)}>
                          <img src={`data:${photoContentType};base64,${photo}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {photoContentType}, {byteSize(photo)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('photo')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_photo" type="file" onChange={onBlobChange(true, 'photo')} accept="image/*" />
                  <AvInput type="hidden" name="photo" value={photo} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="photoUrlLabel" for="branch-photoUrl">
                  <Translate contentKey="carePlannerApp.branch.photoUrl">Photo Url</Translate>
                </Label>
                <AvField id="branch-photoUrl" type="text" name="photoUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="branch-createdDate">
                  <Translate contentKey="carePlannerApp.branch.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="branch-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.branchEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="branch-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.branch.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="branch-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.branchEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="hasExtraDataLabel">
                  <AvInput id="branch-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.branch.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="branch-client">
                  <Translate contentKey="carePlannerApp.branch.client">Client</Translate>
                </Label>
                <AvInput id="branch-client" type="select" className="form-control" name="clientId">
                  <option value="" key="0" />
                  {clients
                    ? clients.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.clientName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/branch" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  clients: storeState.client.entities,
  branchEntity: storeState.branch.entity,
  loading: storeState.branch.loading,
  updating: storeState.branch.updating,
  updateSuccess: storeState.branch.updateSuccess,
});

const mapDispatchToProps = {
  getClients,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BranchUpdate);
