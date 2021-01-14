import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './country.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICountryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CountryDetail = (props: ICountryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { countryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.country.detail.title">Country</Translate> [<b>{countryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="countryName">
              <Translate contentKey="carePlannerApp.country.countryName">Country Name</Translate>
            </span>
          </dt>
          <dd>{countryEntity.countryName}</dd>
          <dt>
            <span id="countryIsoCode">
              <Translate contentKey="carePlannerApp.country.countryIsoCode">Country Iso Code</Translate>
            </span>
          </dt>
          <dd>{countryEntity.countryIsoCode}</dd>
          <dt>
            <span id="countryFlagUrl">
              <Translate contentKey="carePlannerApp.country.countryFlagUrl">Country Flag Url</Translate>
            </span>
          </dt>
          <dd>{countryEntity.countryFlagUrl}</dd>
          <dt>
            <span id="countryCallingCode">
              <Translate contentKey="carePlannerApp.country.countryCallingCode">Country Calling Code</Translate>
            </span>
          </dt>
          <dd>{countryEntity.countryCallingCode}</dd>
          <dt>
            <span id="countryTelDigitLength">
              <Translate contentKey="carePlannerApp.country.countryTelDigitLength">Country Tel Digit Length</Translate>
            </span>
          </dt>
          <dd>{countryEntity.countryTelDigitLength}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.country.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {countryEntity.createdDate ? <TextFormat value={countryEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.country.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {countryEntity.lastUpdatedDate ? (
              <TextFormat value={countryEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.country.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{countryEntity.hasExtraData ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/country" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/country/${countryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ country }: IRootState) => ({
  countryEntity: country.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CountryDetail);
