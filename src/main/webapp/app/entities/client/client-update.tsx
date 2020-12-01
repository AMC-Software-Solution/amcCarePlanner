import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientUpdate = (props: IClientUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { clientEntity, loading, updating } = props;

  const { clientLogo, clientLogoContentType } = clientEntity;

  const handleClose = () => {
    props.history.push('/client' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
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
        ...clientEntity,
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
          <h2 id="carePlannerApp.client.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.client.home.createOrEditLabel">Create or edit a Client</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : clientEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="client-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="client-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="clientNameLabel" for="client-clientName">
                  <Translate contentKey="carePlannerApp.client.clientName">Client Name</Translate>
                </Label>
                <AvField
                  id="client-clientName"
                  type="text"
                  name="clientName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientDescriptionLabel" for="client-clientDescription">
                  <Translate contentKey="carePlannerApp.client.clientDescription">Client Description</Translate>
                </Label>
                <AvField id="client-clientDescription" type="text" name="clientDescription" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="clientLogoLabel" for="clientLogo">
                    <Translate contentKey="carePlannerApp.client.clientLogo">Client Logo</Translate>
                  </Label>
                  <br />
                  {clientLogo ? (
                    <div>
                      {clientLogoContentType ? (
                        <a onClick={openFile(clientLogoContentType, clientLogo)}>
                          <img src={`data:${clientLogoContentType};base64,${clientLogo}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {clientLogoContentType}, {byteSize(clientLogo)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('clientLogo')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_clientLogo" type="file" onChange={onBlobChange(true, 'clientLogo')} accept="image/*" />
                  <AvInput type="hidden" name="clientLogo" value={clientLogo} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="clientLogoUrlLabel" for="client-clientLogoUrl">
                  <Translate contentKey="carePlannerApp.client.clientLogoUrl">Client Logo Url</Translate>
                </Label>
                <AvField id="client-clientLogoUrl" type="text" name="clientLogoUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="clientContactNameLabel" for="client-clientContactName">
                  <Translate contentKey="carePlannerApp.client.clientContactName">Client Contact Name</Translate>
                </Label>
                <AvField id="client-clientContactName" type="text" name="clientContactName" />
              </AvGroup>
              <AvGroup>
                <Label id="clientPhoneLabel" for="client-clientPhone">
                  <Translate contentKey="carePlannerApp.client.clientPhone">Client Phone</Translate>
                </Label>
                <AvField id="client-clientPhone" type="text" name="clientPhone" />
              </AvGroup>
              <AvGroup>
                <Label id="clientEmailLabel" for="client-clientEmail">
                  <Translate contentKey="carePlannerApp.client.clientEmail">Client Email</Translate>
                </Label>
                <AvField id="client-clientEmail" type="text" name="clientEmail" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="client-createdDate">
                  <Translate contentKey="carePlannerApp.client.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="client-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.clientEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="client-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.client.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="client-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.clientEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/client" replace color="info">
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
  clientEntity: storeState.client.entity,
  loading: storeState.client.loading,
  updating: storeState.client.updating,
  updateSuccess: storeState.client.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientUpdate);
