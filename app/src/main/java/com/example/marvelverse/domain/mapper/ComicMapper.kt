package com.example.marvelverse.domain.mapper

import com.example.marvelverse.data.dataSources.local.entities.home.ComicEntity
import com.example.marvelverse.data.dataSources.local.entities.search.ComicSearchEntity
import com.example.marvelverse.data.dataSources.remote.reponses.ComicDto
import com.example.marvelverse.domain.entities.Comic
import javax.inject.Inject

class ComicMapper  @Inject constructor() : Mapper<ComicDto, Comic> {
    override fun map(input: ComicDto): Comic {
        return Comic(
            id = input.id,
            title = input.title,
            description = input.description,
            resourceURI = input.resourceURI,
            pageCount = input.pageCount,
            seriesUri = input.series?.collectionURI,
            charactersUri = input.characters?.collectionURI,
            creatorsUri = input.creators?.collectionURI,
            storiesUri = input.stories?.collectionURI,
            eventsUri = input.events?.collectionURI,
            imageUrl = input.thumbnail?.path + "." + input.thumbnail?.extension
        )
    }
}

class ComicSearchEntityToComicMapper  @Inject constructor(): Mapper<ComicSearchEntity, Comic> {
    override fun map(input: ComicSearchEntity): Comic {
        return Comic(
            id = input.id,
            title = input.title,
            description = input.description,
            charactersUri = input.charactersUri,
            seriesUri = input.seriesUri,
            storiesUri = input.storiesUri,
            eventsUri = input.eventsUri,
            pageCount = input.pageCount,
            creatorsUri = input.creatorsUri,
            resourceURI = input.resourceURI,
            imageUrl = input.imageUrl
        )
    }
}

class ComicToComicSearchEntityMapper  @Inject constructor(): Mapper<Comic, ComicSearchEntity> {
    override fun map(input: Comic): ComicSearchEntity {
        return ComicSearchEntity(
            id = input.id,
            title = input.title,
            charactersUri = input.charactersUri,
            creatorsUri = input.creatorsUri,
            eventsUri = input.eventsUri,
            seriesUri = input.seriesUri,
            storiesUri = input.storiesUri,
            description = input.description,
            pageCount = input.pageCount,
            resourceURI = input.resourceURI,
            imageUrl = input.imageUrl
        )
    }
}


class ComicEntityToComicMapper  @Inject constructor() : Mapper<ComicEntity, Comic> {
    override fun map(input: ComicEntity): Comic {
        return Comic(
            id = input.id,
            title = input.title,
            description = input.description,
            charactersUri = input.charactersUri,
            seriesUri = input.seriesUri,
            storiesUri = input.storiesUri,
            eventsUri = input.eventsUri,
            pageCount = input.pageCount,
            creatorsUri = input.creatorsUri,
            resourceURI = input.resourceURI,
            imageUrl = input.imageUrl
        )
    }
}

class ComicToComicEntityMapper  @Inject constructor() : Mapper<Comic, ComicEntity> {
    override fun map(input: Comic): ComicEntity {
        return ComicEntity(
            id = input.id,
            title = input.title,
            charactersUri = input.charactersUri,
            creatorsUri = input.creatorsUri,
            eventsUri = input.eventsUri,
            seriesUri = input.seriesUri,
            storiesUri = input.storiesUri,
            description = input.description,
            pageCount = input.pageCount,
            resourceURI = input.resourceURI,
            imageUrl = input.imageUrl
        )
    }
}