import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './power-of-attorney.reducer';
import { IPowerOfAttorney } from 'app/shared/model/power-of-attorney.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IPowerOfAttorneyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const PowerOfAttorney = (props: IPowerOfAttorneyProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { powerOfAttorneyList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="power-of-attorney-heading">
        <Translate contentKey="carePlannerApp.powerOfAttorney.home.title">Power Of Attorneys</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.powerOfAttorney.home.createLabel">Create new Power Of Attorney</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {powerOfAttorneyList && powerOfAttorneyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('powerOfAttorneyConsent')}>
                  <Translate contentKey="carePlannerApp.powerOfAttorney.powerOfAttorneyConsent">Power Of Attorney Consent</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('healthAndWelfare')}>
                  <Translate contentKey="carePlannerApp.powerOfAttorney.healthAndWelfare">Health And Welfare</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('healthAndWelfareName')}>
                  <Translate contentKey="carePlannerApp.powerOfAttorney.healthAndWelfareName">Health And Welfare Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('propertyAndFinAffairs')}>
                  <Translate contentKey="carePlannerApp.powerOfAttorney.propertyAndFinAffairs">Property And Fin Affairs</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('propertyAndFinAffairsName')}>
                  <Translate contentKey="carePlannerApp.powerOfAttorney.propertyAndFinAffairsName">Property And Fin Affairs Name</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.powerOfAttorney.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.powerOfAttorney.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.powerOfAttorney.serviceUser">Service User</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.powerOfAttorney.witnessedBy">Witnessed By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {powerOfAttorneyList.map((powerOfAttorney, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${powerOfAttorney.id}`} color="link" size="sm">
                      {powerOfAttorney.id}
                    </Button>
                  </td>
                  <td>{powerOfAttorney.powerOfAttorneyConsent ? 'true' : 'false'}</td>
                  <td>{powerOfAttorney.healthAndWelfare ? 'true' : 'false'}</td>
                  <td>{powerOfAttorney.healthAndWelfareName}</td>
                  <td>{powerOfAttorney.propertyAndFinAffairs ? 'true' : 'false'}</td>
                  <td>{powerOfAttorney.propertyAndFinAffairsName}</td>
                  <td>
                    {powerOfAttorney.lastUpdatedDate ? (
                      <TextFormat type="date" value={powerOfAttorney.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{powerOfAttorney.clientId}</td>
                  <td>
                    {powerOfAttorney.serviceUserServiceUserCode ? (
                      <Link to={`service-user/${powerOfAttorney.serviceUserId}`}>{powerOfAttorney.serviceUserServiceUserCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {powerOfAttorney.witnessedByEmployeeCode ? (
                      <Link to={`employee/${powerOfAttorney.witnessedById}`}>{powerOfAttorney.witnessedByEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${powerOfAttorney.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${powerOfAttorney.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${powerOfAttorney.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="carePlannerApp.powerOfAttorney.home.notFound">No Power Of Attorneys found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={powerOfAttorneyList && powerOfAttorneyList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ powerOfAttorney }: IRootState) => ({
  powerOfAttorneyList: powerOfAttorney.entities,
  loading: powerOfAttorney.loading,
  totalItems: powerOfAttorney.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PowerOfAttorney);
