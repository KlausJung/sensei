package com.senseidb.search.relevance;

import com.linkedin.bobo.facets.data.MultiValueFacetDataCache;
import com.linkedin.bobo.facets.data.MultiValueWithWeightFacetDataCache;
import com.linkedin.bobo.facets.data.TermIntList;

public class WeightedMFacetInt extends MFacetInt implements WeightedMFacet
{

  public WeightedMFacetInt(MultiValueFacetDataCache mDataCaches)
  {
    super(mDataCaches);
    
    MultiValueWithWeightFacetDataCache wmDataCaches = (MultiValueWithWeightFacetDataCache) mDataCaches;
    _weightArray = wmDataCaches._weightArray;
    weightBuf = new int[1024];
  }

  @Override
  public void refresh(int id)
  {
    _length = _nestedArray.getData(id, _buf);
    _weightArray.getData(id, weightBuf);
  }

  public boolean hasWeight(int target){
    
    for(int i=0; i< this._length; i++)
      if(((TermIntList) _mTermList).getPrimitiveValue(_buf[i]) == target)
      {
        _weight[0] = weightBuf[i];
        return true;
      }
              
    return false;
  }

  @Override
  public int getWeight()
  {
    return _weight[0];
  }
}
