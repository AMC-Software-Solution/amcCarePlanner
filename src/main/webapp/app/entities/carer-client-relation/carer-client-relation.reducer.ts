import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICarerClientRelation, defaultValue } from 'app/shared/model/carer-client-relation.model';

export const ACTION_TYPES = {
  FETCH_CARERCLIENTRELATION_LIST: 'carerClientRelation/FETCH_CARERCLIENTRELATION_LIST',
  FETCH_CARERCLIENTRELATION: 'carerClientRelation/FETCH_CARERCLIENTRELATION',
  CREATE_CARERCLIENTRELATION: 'carerClientRelation/CREATE_CARERCLIENTRELATION',
  UPDATE_CARERCLIENTRELATION: 'carerClientRelation/UPDATE_CARERCLIENTRELATION',
  DELETE_CARERCLIENTRELATION: 'carerClientRelation/DELETE_CARERCLIENTRELATION',
  RESET: 'carerClientRelation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICarerClientRelation>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CarerClientRelationState = Readonly<typeof initialState>;

// Reducer

export default (state: CarerClientRelationState = initialState, action): CarerClientRelationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CARERCLIENTRELATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CARERCLIENTRELATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CARERCLIENTRELATION):
    case REQUEST(ACTION_TYPES.UPDATE_CARERCLIENTRELATION):
    case REQUEST(ACTION_TYPES.DELETE_CARERCLIENTRELATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CARERCLIENTRELATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CARERCLIENTRELATION):
    case FAILURE(ACTION_TYPES.CREATE_CARERCLIENTRELATION):
    case FAILURE(ACTION_TYPES.UPDATE_CARERCLIENTRELATION):
    case FAILURE(ACTION_TYPES.DELETE_CARERCLIENTRELATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CARERCLIENTRELATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CARERCLIENTRELATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CARERCLIENTRELATION):
    case SUCCESS(ACTION_TYPES.UPDATE_CARERCLIENTRELATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CARERCLIENTRELATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/carer-client-relations';

// Actions

export const getEntities: ICrudGetAllAction<ICarerClientRelation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CARERCLIENTRELATION_LIST,
    payload: axios.get<ICarerClientRelation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICarerClientRelation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CARERCLIENTRELATION,
    payload: axios.get<ICarerClientRelation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICarerClientRelation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CARERCLIENTRELATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICarerClientRelation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CARERCLIENTRELATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICarerClientRelation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CARERCLIENTRELATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
