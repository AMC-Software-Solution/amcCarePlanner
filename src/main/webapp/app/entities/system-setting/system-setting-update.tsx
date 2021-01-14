import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './system-setting.reducer';
import { ISystemSetting } from 'app/shared/model/system-setting.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISystemSettingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemSettingUpdate = (props: ISystemSettingUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { systemSettingEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/system-setting' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    if (errors.length === 0) {
      const entity = {
        ...systemSettingEntity,
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
          <h2 id="carePlannerApp.systemSetting.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.systemSetting.home.createOrEditLabel">Create or edit a SystemSetting</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : systemSettingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="system-setting-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="system-setting-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fieldNameLabel" for="system-setting-fieldName">
                  <Translate contentKey="carePlannerApp.systemSetting.fieldName">Field Name</Translate>
                </Label>
                <AvField
                  id="system-setting-fieldName"
                  type="text"
                  name="fieldName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fieldValueLabel" for="system-setting-fieldValue">
                  <Translate contentKey="carePlannerApp.systemSetting.fieldValue">Field Value</Translate>
                </Label>
                <AvField
                  id="system-setting-fieldValue"
                  type="text"
                  name="fieldValue"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="defaultValueLabel" for="system-setting-defaultValue">
                  <Translate contentKey="carePlannerApp.systemSetting.defaultValue">Default Value</Translate>
                </Label>
                <AvField
                  id="system-setting-defaultValue"
                  type="text"
                  name="defaultValue"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="settingEnabledLabel">
                  <AvInput id="system-setting-settingEnabled" type="checkbox" className="form-check-input" name="settingEnabled" />
                  <Translate contentKey="carePlannerApp.systemSetting.settingEnabled">Setting Enabled</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="system-setting-createdDate">
                  <Translate contentKey="carePlannerApp.systemSetting.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="system-setting-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.systemSettingEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedDateLabel" for="system-setting-updatedDate">
                  <Translate contentKey="carePlannerApp.systemSetting.updatedDate">Updated Date</Translate>
                </Label>
                <AvInput
                  id="system-setting-updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.systemSettingEntity.updatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="system-setting-clientId">
                  <Translate contentKey="carePlannerApp.systemSetting.clientId">Client Id</Translate>
                </Label>
                <AvField id="system-setting-clientId" type="string" className="form-control" name="clientId" />
              </AvGroup>
              <AvGroup check>
                <Label id="hasExtraDataLabel">
                  <AvInput id="system-setting-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.systemSetting.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/system-setting" replace color="info">
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
  systemSettingEntity: storeState.systemSetting.entity,
  loading: storeState.systemSetting.loading,
  updating: storeState.systemSetting.updating,
  updateSuccess: storeState.systemSetting.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemSettingUpdate);
