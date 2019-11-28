Kotlin Flows and Coroutines
==============================================

```kotlin
   
 class SearchViewModel(searchRepository: SearchRepository) : ViewModel() {
    val query = MutableLiveData<String>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    val repo = query.asFlow()
        .debounce(300)
        .filter {
            it.trim().isEmpty().not()
        }
        .distinctUntilChanged()
        .flatMapLatest {
            searchRepository.searchRepo(it).asFlow()
        }.asLiveData()

}
    
```

### Screenshots
<p align="center">
<img src="art/device-2019-11-28-173356.png" height="400" alt="Screenshot" gravity="center"/> 
</p>


License
-------

    Copyright 2019 Hari Singh Kulhari

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
