import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './communication.reducer';
import { ICommunication } from 'app/shared/model/communication.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICommunicationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommunicationUpdate = (props: ICommunicationUpdateProps) => {
  const [serviceUserId, setServiceUserId] = useState('0');
  const [communicatedById, setCommunicatedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { communicationEntity, serviceUsers, employees, loading, updating } = props;

  const { attachment, attachmentContentType } = communicationEntity;

  const handleClose = () => {
    props.history.push('/communication' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceUsers();
    props.getEmployees();
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
    values.communicationDate = convertDateTimeToServer(values.communicationDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...communicationEntity,
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
          <h2 id="carePlannerApp.communication.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.communication.home.createOrEditLabel">Create or edit a Communication</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : communicationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="communication-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="communication-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="communicationTypeLabel" for="communication-communicationType">
                  <Translate contentKey="carePlannerApp.communication.communicationType">Communication Type</Translate>
                </Label>
                <AvInput
                  id="communication-communicationType"
                  type="select"
                  className="form-control"
                  name="communicationType"
                  value={(!isNew && communicationEntity.communicationType) || 'EMAIL'}
                >
                  <option value="EMAIL">{translate('carePlannerApp.CommunicationType.EMAIL')}</option>
                  <option value="SMS">{translate('carePlannerApp.CommunicationType.SMS')}</option>
                  <option value="TELEPHONE">{translate('carePlannerApp.CommunicationType.TELEPHONE')}</option>
                  <option value="MAIL">{translate('carePlannerApp.CommunicationType.MAIL')}</option>
                  <option value="IN_PERSON">{translate('carePlannerApp.CommunicationType.IN_PERSON')}</option>
                  <option value="OTHER">{translate('carePlannerApp.CommunicationType.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="communication-note">
                  <Translate contentKey="carePlannerApp.communication.note">Note</Translate>
                </Label>
                <AvField id="communication-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="communicationDateLabel" for="communication-communicationDate">
                  <Translate contentKey="carePlannerApp.communication.communicationDate">Communication Date</Translate>
                </Label>
                <AvInput
                  id="communication-communicationDate"
                  type="datetime-local"
                  className="form-control"
                  name="communicationDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.communicationEntity.communicationDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="attachmentLabel" for="attachment">
                    <Translate contentKey="carePlannerApp.communication.attachment">Attachment</Translate>
                  </Label>
                  <br />
                  {attachment ? (
                    <div>
                      {attachmentContentType ? (
                        <a onClick={openFile(attachmentContentType, attachment)}>
                          <img src={`data:${attachmentContentType};base64,${attachment}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {attachmentContentType}, {byteSize(attachment)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('attachment')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_attachment" type="file" onChange={onBlobChange(true, 'attachment')} accept="image/*" />
                  <AvInput type="hidden" name="attachment" value={attachment} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="attachmentUrlLabel" for="communication-attachmentUrl">
                  <Translate contentKey="carePlannerApp.communication.attachmentUrl">Attachment Url</Translate>
                </Label>
                <AvField id="communication-attachmentUrl" type="text" name="attachmentUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="communication-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.communication.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="communication-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.communicationEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="communication-clientId">
                  <Translate contentKey="carePlannerApp.communication.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="communication-clientId"
                  type="string"
                  className="form-control"
                  name="clientId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="communication-serviceUser">
                  <Translate contentKey="carePlannerApp.communication.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="communication-serviceUser" type="select" className="form-control" name="serviceUserId">
                  <option value="" key="0" />
                  {serviceUsers
                    ? serviceUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.serviceUserCode}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="communication-communicatedBy">
                  <Translate contentKey="carePlannerApp.communication.communicatedBy">Communicated By</Translate>
                </Label>
                <AvInput id="communication-communicatedBy" type="select" className="form-control" name="communicatedById">
                  <option value="" key="0" />
                  {employees
                    ? employees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.employeeCode}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/communication" replace color="info">
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
  serviceUsers: storeState.serviceUser.entities,
  employees: storeState.employee.entities,
  communicationEntity: storeState.communication.entity,
  loading: storeState.communication.loading,
  updating: storeState.communication.updating,
  updateSuccess: storeState.communication.updateSuccess,
});

const mapDispatchToProps = {
  getServiceUsers,
  getEmployees,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommunicationUpdate);
