import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './currency.reducer';
import { ICurrency } from 'app/shared/model/currency.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICurrencyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CurrencyUpdate = (props: ICurrencyUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { currencyEntity, loading, updating } = props;

  const { currencyLogo, currencyLogoContentType } = currencyEntity;

  const handleClose = () => {
    props.history.push('/currency' + props.location.search);
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
    if (errors.length === 0) {
      const entity = {
        ...currencyEntity,
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
          <h2 id="carePlannerApp.currency.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.currency.home.createOrEditLabel">Create or edit a Currency</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : currencyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="currency-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="currency-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="currencyLabel" for="currency-currency">
                  <Translate contentKey="carePlannerApp.currency.currency">Currency</Translate>
                </Label>
                <AvField
                  id="currency-currency"
                  type="text"
                  name="currency"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    maxLength: { value: 99, errorMessage: translate('entity.validation.maxlength', { max: 99 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="currencyIsoCodeLabel" for="currency-currencyIsoCode">
                  <Translate contentKey="carePlannerApp.currency.currencyIsoCode">Currency Iso Code</Translate>
                </Label>
                <AvField
                  id="currency-currencyIsoCode"
                  type="text"
                  name="currencyIsoCode"
                  validate={{
                    maxLength: { value: 3, errorMessage: translate('entity.validation.maxlength', { max: 3 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="currencySymbolLabel" for="currency-currencySymbol">
                  <Translate contentKey="carePlannerApp.currency.currencySymbol">Currency Symbol</Translate>
                </Label>
                <AvField id="currency-currencySymbol" type="text" name="currencySymbol" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="currencyLogoLabel" for="currencyLogo">
                    <Translate contentKey="carePlannerApp.currency.currencyLogo">Currency Logo</Translate>
                  </Label>
                  <br />
                  {currencyLogo ? (
                    <div>
                      {currencyLogoContentType ? (
                        <a onClick={openFile(currencyLogoContentType, currencyLogo)}>
                          <img src={`data:${currencyLogoContentType};base64,${currencyLogo}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {currencyLogoContentType}, {byteSize(currencyLogo)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('currencyLogo')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_currencyLogo" type="file" onChange={onBlobChange(true, 'currencyLogo')} accept="image/*" />
                  <AvInput type="hidden" name="currencyLogo" value={currencyLogo} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="currencyLogoUrlLabel" for="currency-currencyLogoUrl">
                  <Translate contentKey="carePlannerApp.currency.currencyLogoUrl">Currency Logo Url</Translate>
                </Label>
                <AvField id="currency-currencyLogoUrl" type="text" name="currencyLogoUrl" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/currency" replace color="info">
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
  currencyEntity: storeState.currency.entity,
  loading: storeState.currency.loading,
  updating: storeState.currency.updating,
  updateSuccess: storeState.currency.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(CurrencyUpdate);
