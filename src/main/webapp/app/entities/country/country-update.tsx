import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './country.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICountryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CountryUpdate = (props: ICountryUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { countryEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/country' + props.location.search);
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
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...countryEntity,
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
          <h2 id="carePlannerApp.country.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.country.home.createOrEditLabel">Create or edit a Country</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : countryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="country-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="country-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="countryNameLabel" for="country-countryName">
                  <Translate contentKey="carePlannerApp.country.countryName">Country Name</Translate>
                </Label>
                <AvField
                  id="country-countryName"
                  type="text"
                  name="countryName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="countryIsoCodeLabel" for="country-countryIsoCode">
                  <Translate contentKey="carePlannerApp.country.countryIsoCode">Country Iso Code</Translate>
                </Label>
                <AvField
                  id="country-countryIsoCode"
                  type="text"
                  name="countryIsoCode"
                  validate={{
                    maxLength: { value: 6, errorMessage: translate('entity.validation.maxlength', { max: 6 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="countryFlagUrlLabel" for="country-countryFlagUrl">
                  <Translate contentKey="carePlannerApp.country.countryFlagUrl">Country Flag Url</Translate>
                </Label>
                <AvField id="country-countryFlagUrl" type="text" name="countryFlagUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="countryCallingCodeLabel" for="country-countryCallingCode">
                  <Translate contentKey="carePlannerApp.country.countryCallingCode">Country Calling Code</Translate>
                </Label>
                <AvField id="country-countryCallingCode" type="text" name="countryCallingCode" />
              </AvGroup>
              <AvGroup>
                <Label id="countryTelDigitLengthLabel" for="country-countryTelDigitLength">
                  <Translate contentKey="carePlannerApp.country.countryTelDigitLength">Country Tel Digit Length</Translate>
                </Label>
                <AvField id="country-countryTelDigitLength" type="string" className="form-control" name="countryTelDigitLength" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="country-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.country.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="country-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.countryEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/country" replace color="info">
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
  countryEntity: storeState.country.entity,
  loading: storeState.country.loading,
  updating: storeState.country.updating,
  updateSuccess: storeState.country.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CountryUpdate);
