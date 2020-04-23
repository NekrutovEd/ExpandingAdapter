package com.expanding.adapter

import android.net.Uri
import com.expanding.adapter.module.Group
import com.expanding.adapter.module.Miniature
import com.expanding.adapter.module.MiniatureItem
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class MainInteractor : IMain.Interactor {

    private val list by lazy { createMockList() }

    private val subject = BehaviorSubject.createDefault<List<Group>>(list)

    override fun loadGroups(): Observable<List<Group>> = subject

    override fun attachFile(groupId: Long?, imageUri: Uri?) {
        imageUri ?: return
        val group = list.find { it.groupId == groupId }
        group?.miniatures?.add(Miniature(miniatureUri = imageUri))
//        group?.let { subject.onNext(listOf(it)) }
        subject.onNext(list)
    }

    override fun clearGroup(groupId: Long) {
        val group = list.find { it.groupId == groupId }
        group?.miniatures?.clear()
//        group?.let { subject.onNext(listOf(it)) }
        subject.onNext(list)
    }

    override fun stopLoading(miniatureId: UUID) {
        val group = list.find { group ->
            group.miniatures.find { it.id == miniatureId }?.also { it.status = MiniatureItem.MiniatureStatus.Fail } != null
        }

//        group?.let { subject.onNext(listOf(it)) }
        subject.onNext(list)
    }

    override fun upload(miniatureId: UUID) {
        val group = list.find { group ->
            group.miniatures.find { it.id == miniatureId }?.also { it.status = MiniatureItem.MiniatureStatus.Complete } != null
        }

//        group?.let { subject.onNext(listOf(it)) }
        subject.onNext(list)
    }
}