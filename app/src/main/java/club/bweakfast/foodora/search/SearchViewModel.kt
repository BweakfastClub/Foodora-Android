package club.bweakfast.foodora.search

import club.bweakfast.foodora.network.ServerResponse
import io.reactivex.Single
import club.bweakfast.foodora.network.mapResponse
import club.bweakfast.foodora.recipe.Recipe
import javax.inject.Inject

/**
 * Created by silve on 3/5/2018.
 */

class SearchViewModel @Inject constructor(private val searchService: SearchService) {
    fun search(query: String): Single<ServerResponse<List<Recipe>>> =
        searchService.search(query).mapResponse()
}