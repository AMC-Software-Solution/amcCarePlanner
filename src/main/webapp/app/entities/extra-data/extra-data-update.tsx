import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './extra-data.reducer';
import { IExtraData } from 'app/shared/model/extra-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExtraDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExtraDataUpdate = (props: IExtraDataUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { extraDataEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/extra-data' + props.location.search);
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
    values.extraDataDate = convertDateTimeToServer(values.extraDataDate);

    if (errors.length === 0) {
      const entity = {
        ...extraDataEntity,
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
          <h2 id="carePlannerApp.extraData.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.extraData.home.createOrEditLabel">Create or edit a ExtraData</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : extraDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="extra-data-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="extra-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="entityNameLabel" for="extra-data-entityName">
                  <Translate contentKey="carePlannerApp.extraData.entityName">Entity Name</Translate>
                </Label>
                <AvField
                  id="extra-data-entityName"
                  type="text"
                  name="entityName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="entityIdLabel" for="extra-data-entityId">
                  <Translate contentKey="carePlannerApp.extraData.entityId">Entity Id</Translate>
                </Label>
                <AvField
                  id="extra-data-entityId"
                  type="string"
                  className="form-control"
                  name="entityId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="extraDataKeyLabel" for="extra-data-extraDataKey">
                  <Translate contentKey="carePlannerApp.extraData.extraDataKey">Extra Data Key</Translate>
                </Label>
                <AvField id="extra-data-extraDataKey" type="text" name="extraDataKey" />
              </AvGroup>
              <AvGroup>
                <Label id="extraDataValueLabel" for="extra-data-extraDataValue">
                  <Translate contentKey="carePlannerApp.extraData.extraDataValue">Extra Data Value</Translate>
                </Label>
                <AvField id="extra-data-extraDataValue" type="text" name="extraDataValue" />
              </AvGroup>
              <AvGroup>
                <Label id="extraDataValueDataTypeLabel" for="extra-data-extraDataValueDataType">
                  <Translate contentKey="carePlannerApp.extraData.extraDataValueDataType">Extra Data Value Data Type</Translate>
                </Label>
                <AvInput
                  id="extra-data-extraDataValueDataType"
                  type="select"
                  className="form-control"
                  name="extraDataValueDataType"
                  value={(!isNew && extraDataEntity.extraDataValueDataType) || 'STRING'}
                >
                  <option value="STRING">{translate('carePlannerApp.DataType.STRING')}</option>
                  <option value="BOOLEAN">{translate('carePlannerApp.DataType.BOOLEAN')}</option>
                  <option value="NUMBER">{translate('carePlannerApp.DataType.NUMBER')}</option>
                  <option value="DATE">{translate('carePlannerApp.DataType.DATE')}</option>
                  <option value="FILE">{translate('carePlannerApp.DataType.FILE')}</option>
                  <option value="OBJECT">{translate('carePlannerApp.DataType.OBJECT')}</option>
                  <option value="ARRAY">{translate('carePlannerApp.DataType.ARRAY')}</option>
                  <option value="GEO_POINT">{translate('carePlannerApp.DataType.GEO_POINT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="extraDataDescriptionLabel" for="extra-data-extraDataDescription">
                  <Translate contentKey="carePlannerApp.extraData.extraDataDescription">Extra Data Description</Translate>
                </Label>
                <AvField id="extra-data-extraDataDescription" type="text" name="extraDataDescription" />
              </AvGroup>
              <AvGroup>
                <Label id="extraDataDateLabel" for="extra-data-extraDataDate">
                  <Translate contentKey="carePlannerApp.extraData.extraDataDate">Extra Data Date</Translate>
                </Label>
                <AvInput
                  id="extra-data-extraDataDate"
                  type="datetime-local"
                  className="form-control"
                  name="extraDataDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.extraDataEntity.extraDataDate)}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="hasExtraDataLabel">
                  <AvInput id="extra-data-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.extraData.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/extra-data" replace color="info">
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
  extraDataEntity: storeState.extraData.entity,
  loading: storeState.extraData.loading,
  updating: storeState.extraData.updating,
  updateSuccess: storeState.extraData.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExtraDataUpdate);
